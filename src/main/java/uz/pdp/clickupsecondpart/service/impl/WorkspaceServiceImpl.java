package uz.pdp.clickupsecondpart.service.impl;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.*;
import uz.pdp.clickupsecondpart.entity.enums.WorkspacePermissionName;
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

    private final WorkspaceUserRepository workspaceUserRepository;

    private final WorkspaceRoleRepository workspaceRoleRepository;

    private final WorkspacePermissionRepository workspacePermissionRepository;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository repository, UserRepository userRepository, AttachmentRepository attachmentRepository, WorkspaceUserRepository workspaceUserRepository, WorkspaceRoleRepository workspaceRoleRepository, WorkspacePermissionRepository workspacePermissionRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.attachmentRepository = attachmentRepository;
        this.workspaceUserRepository = workspaceUserRepository;
        this.workspaceRoleRepository = workspaceRoleRepository;
        this.workspacePermissionRepository = workspacePermissionRepository;
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
        Workspace workspace = repository.save(new Workspace(
                dto.getName(), dto.getColor(), user,
                dto.getAvatarId() == null ? null : attachmentRepository.findById(dto.getAvatarId()).orElseThrow(() -> new OpenApiResourceNotFoundException("Attachment not found"))
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
        return null;
    }

    @Override
    public ResponseEntity<?> changeWorkspace(Long workspaceId, UUID userId) {
        return null;
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
