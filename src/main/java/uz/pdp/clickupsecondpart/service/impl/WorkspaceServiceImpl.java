package uz.pdp.clickupsecondpart.service.impl;

import org.springdoc.api.OpenApiResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.*;
import uz.pdp.clickupsecondpart.entity.enums.WorkspacePermissionName;
import uz.pdp.clickupsecondpart.entity.enums.WorkspaceRoleName;
import uz.pdp.clickupsecondpart.payload.WorkspaceDTO;
import uz.pdp.clickupsecondpart.payload.resp.WorkspaceResponse;
import uz.pdp.clickupsecondpart.repository.*;
import uz.pdp.clickupsecondpart.service.WorkspaceService;

import java.sql.Timestamp;
import java.util.*;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class WorkspaceServiceImpl implements WorkspaceService {

    private final WorkspaceRepository repository;

    private final AttachmentRepository attachmentRepository;

    private final WorkspaceUserRepository workspaceUserRepository;

    private final WorkspaceRoleRepository workspaceRoleRepository;

    private final WorkspacePermissionRepository workspacePermissionRepository;

    @Autowired
    public WorkspaceServiceImpl(WorkspaceRepository repository, AttachmentRepository attachmentRepository, WorkspaceUserRepository workspaceUserRepository, WorkspaceRoleRepository workspaceRoleRepository, WorkspacePermissionRepository workspacePermissionRepository) {
        this.repository = repository;
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

        WorkspaceRole workspaceOwnerRole = workspaceRoleRepository.save(
                new WorkspaceRole(
                        workspace,
                        WorkspaceRoleName.ROLE_OWNER.name(),
                        null
                )
        );

        Set<WorkspacePermission> permissions = new HashSet<>();
        for (WorkspacePermissionName permissionName : WorkspacePermissionName.values()) {
            permissions.add(new WorkspacePermission(workspaceOwnerRole, permissionName));
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
        return null;
    }
}
