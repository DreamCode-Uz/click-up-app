package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.component.SendMail;
import uz.pdp.clickupsecondpart.entity.*;
import uz.pdp.clickupsecondpart.entity.enums.ActionType;
import uz.pdp.clickupsecondpart.entity.enums.WorkspacePermissionName;
import uz.pdp.clickupsecondpart.payload.MemberDTO;
import uz.pdp.clickupsecondpart.payload.WorkspaceDTO;
import uz.pdp.clickupsecondpart.payload.resp.WorkspaceResponse;
import uz.pdp.clickupsecondpart.repository.*;
import uz.pdp.clickupsecondpart.service.WorkspaceService;

import java.sql.Timestamp;
import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;
import static uz.pdp.clickupsecondpart.entity.enums.WorkspaceRoleName.*;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository repository;

    private final UserRepository userRepository;

    private final AttachmentRepository attachmentRepository;

    private final AttachmentContentRepository contentRepository;

    private final WorkspaceUserRepository workspaceUserRepository;

    private final WorkspaceRoleRepository workspaceRoleRepository;

    private final WorkspacePermissionRepository workspacePermissionRepository;

    private final SendMail mail;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository repository, UserRepository userRepository, AttachmentRepository attachmentRepository, AttachmentContentRepository contentRepository, WorkspaceUserRepository workspaceUserRepository, WorkspaceRoleRepository workspaceRoleRepository, WorkspacePermissionRepository workspacePermissionRepository, SendMail mail) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.contentRepository = contentRepository;
        this.workspaceUserRepository = workspaceUserRepository;
        this.workspaceRoleRepository = workspaceRoleRepository;
        this.workspacePermissionRepository = workspacePermissionRepository;
        this.mail = mail;
    }

    @Override
    public ResponseEntity<?> getAll() {
        return null;
    }

    @Override
    public ResponseEntity<?> getOne(Long workspaceId) {
        Optional<Workspace> optionalWorkspace = repository.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace not found");
        return ok(new WorkspaceResponse(optionalWorkspace.get()));
    }

    @Override
    public ResponseEntity<?> create(WorkspaceDTO dto, User user) {
        if (repository.existsByOwner_IdAndName(user.getId(), dto.getName()))
            return status(UNPROCESSABLE_ENTITY).body("Workspace already exists");

        if (dto.getAvatarId() != null && !attachmentRepository.existsById(dto.getAvatarId()))
            return status(NOT_FOUND).body("Avatar ID not found");

        Workspace workspace = repository.save(new Workspace(
                dto.getName(),
                dto.getColor(),
                user,
                dto.getAvatarId()
        ));

        //  WORKSPACE ROLE OCHISH
        WorkspaceRole workspaceOwnerRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, ROLE_OWNER.name(), null));
        WorkspaceRole workspaceAdminRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, ROLE_ADMIN.name(), null));
        WorkspaceRole workspaceMemberRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, ROLE_MEMBER.name(), null));
        WorkspaceRole workspaceGuestRole = workspaceRoleRepository.save(new WorkspaceRole(workspace, ROLE_GUEST.name(), null));

        //  OWNERGA TIZIMDA MAVJUD HUQUQLARNI BERISH
        Set<WorkspacePermission> permissions = new HashSet<>();
        for (WorkspacePermissionName permissionName : WorkspacePermissionName.values()) {
            WorkspacePermission permission = new WorkspacePermission(workspaceOwnerRole, permissionName);
            permissions.add(permission);
            if (permissionName.getWorkspaceRoleNames().contains(ROLE_ADMIN.name())) {
                permissions.add(new WorkspacePermission(workspaceAdminRole, permissionName));
            }
            if (permissionName.getWorkspaceRoleNames().contains(ROLE_MEMBER.name())) {
                permissions.add(new WorkspacePermission(workspaceMemberRole, permissionName));
            }
            if (permissionName.getWorkspaceRoleNames().contains(ROLE_GUEST.name())) {
                permissions.add(new WorkspacePermission(workspaceGuestRole, permissionName));
            }
        }
        workspacePermissionRepository.saveAll(permissions);

        workspaceUserRepository.save(
                new WorkspaceUser(
                        workspace,
                        user,
                        workspaceOwnerRole,
                        Timestamp.from(new Date().toInstant()),
                        Timestamp.from(new Date().toInstant())
                )
        );
        return status(CREATED).body("Workspace created");
    }

    @Override
    public ResponseEntity<?> edit(Long workspaceId, WorkspaceDTO dto) {
        Optional<Workspace> optionalWorkspace = repository.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace not found");
        Workspace workspace = optionalWorkspace.get();
        if (dto.getAvatarId() != null && attachmentRepository.existsById(dto.getAvatarId())) {
            if (workspace.getAvatarId() != null && contentRepository.existsByAttachment_Id(workspace.getAvatarId())) {
                contentRepository.deleteByAttachmentId(workspace.getAvatarId());
            }
            workspace.setAvatarId(dto.getAvatarId());
        }
        workspace.setName(dto.getName());
        workspace.setColor(dto.getColor());
        repository.save(workspace);
        return status(CREATED).body("Workspace edited");
    }

    //    todo: change workspace coming...
    @Override
    public ResponseEntity<?> changeWorkspace(Long workspaceId, UUID userId) {
        return null;
    }

    @Override
    public ResponseEntity<?> addOrEditOrRemoveWorkspaceUser(Long workspaceId, MemberDTO dto) {
        Optional<Workspace> optionalWorkspace = repository.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace not found");
        Optional<WorkspaceRole> optionalWorkspaceRole = workspaceRoleRepository.findById(dto.getRoleId());
        if (optionalWorkspaceRole.isEmpty() && !dto.getActionType().equals(ActionType.DELETE))
            return status(NOT_FOUND).body("Workspace role not found");

        switch (dto.getActionType()) {
            case ADD -> {
                Optional<User> optionalUser = userRepository.findById(dto.getUserId());
                if (optionalUser.isEmpty()) return status(NOT_FOUND).body("User not found");
                WorkspaceUser workspaceUser = workspaceUserRepository.save(new WorkspaceUser(
                        optionalWorkspace.get(),
                        optionalUser.get(),
                        optionalWorkspaceRole.get(),
                        Timestamp.from(new Date().toInstant()),
                        null
                ));

                String resp = String.format(WORKSPACE_JOINED_URL, workspaceId, workspaceUser.getId());
                mail.sendMail("Workspace joined", resp, optionalUser.get().getEmail());
                return status(CREATED).body("User added to workspace");
            }
            case EDIT -> {
                Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, dto.getUserId());
                if (optionalWorkspaceUser.isEmpty()) return status(NOT_FOUND).body("Workspace user not found");
                WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
                workspaceUser.setWorkspaceRole(optionalWorkspaceRole.get());
                workspaceUserRepository.save(workspaceUser);
                return status(OK).body("User edited in workspace");
            }
            case DELETE -> {
                workspaceUserRepository.deleteByWorkspaceIdAndUserId(workspaceId, dto.getUserId());
                return status(NO_CONTENT).body("User deleted from workspace");
            }
            default -> {
                return status(BAD_REQUEST).body("Invalid action type");
            }
        }
    }

    @Override
    public ResponseEntity<?> joinToWorkspace(Long workspaceId, UUID userId) {
        if (!repository.existsById(workspaceId)) return status(NOT_FOUND).body("Workspace not found");
        Optional<WorkspaceUser> optionalWorkspaceUser = workspaceUserRepository.findByWorkspaceIdAndUserId(workspaceId, userId);
        if (optionalWorkspaceUser.isEmpty()) return status(NOT_FOUND).body("Workspace user not found");
        WorkspaceUser workspaceUser = optionalWorkspaceUser.get();
        workspaceUser.setDataJoined(Timestamp.from(new Date().toInstant()));
        workspaceUserRepository.save(workspaceUser);
        return status(CREATED).body("User joined to workspace");
    }

    @Override
    public ResponseEntity<?> delete(Long workspaceId) {
        Optional<Workspace> optionalWorkspace = repository.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace not found");
        try {
            repository.delete(optionalWorkspace.get());
            return status(NO_CONTENT).body("Workspace deleted");
        } catch (Exception e) {
            return status(BAD_REQUEST).body("Workspace not deleted");
        }
    }
}
