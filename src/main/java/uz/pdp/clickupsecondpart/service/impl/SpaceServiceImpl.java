package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.Attachment;
import uz.pdp.clickupsecondpart.entity.Space;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.entity.Workspace;
import uz.pdp.clickupsecondpart.entity.enums.AccessType;
import uz.pdp.clickupsecondpart.payload.SpaceDTO;
import uz.pdp.clickupsecondpart.repository.*;
import uz.pdp.clickupsecondpart.service.SpaceService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;

    private final WorkspaceRepository workspaceRepository;

    private final AttachmentRepository attachmentRepository;
    private final AttachmentContentRepository attachmentContentRepository;

    @Autowired
    public SpaceServiceImpl(SpaceRepository spaceRepository, WorkspaceRepository workspaceRepository, AttachmentRepository attachmentRepository, AttachmentContentRepository attachmentContentRepository) {
        this.spaceRepository = spaceRepository;
        this.workspaceRepository = workspaceRepository;
        this.attachmentRepository = attachmentRepository;
        this.attachmentContentRepository = attachmentContentRepository;
    }


    @Override
    public ResponseEntity<?> getAllSpaces(Long workspaceId, User user) {
        List<Space> optionalSpace = spaceRepository.findAllByWorkspace_IdAndOwner_Id(workspaceId, user.getId());
        return ok(optionalSpace);
    }

    @Override
    public ResponseEntity<?> getSpace(UUID spaceId) {
        Optional<Space> optionalSpace = spaceRepository.findById(spaceId);
        if (optionalSpace.isEmpty()) return status(NOT_FOUND).body("Space not found");
        return ok(optionalSpace.get());
    }

    @Override
    public ResponseEntity<?> createSpace(Long workspaceId, SpaceDTO dto, User user) {
        if (!Arrays.stream(AccessType.values()).toList().contains(dto.getAccessType()))
            return status(BAD_REQUEST).body("Invalid access type");
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace not found");
        if (spaceRepository.existsByNameAndWorkspaceId(dto.getName(), workspaceId))
            return status(UNPROCESSABLE_ENTITY).body("Space already exists");
        Space space = new Space(
                dto.getName(),
                dto.getColor(),
                dto.getAccessType(),
                optionalWorkspace.get(),
                user
        );
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(dto.getAvatarId());
        optionalAttachment.ifPresent(space::setAvatar);
        return status(CREATED).body(spaceRepository.save(space));
    }

    // Space ayanan shu workspacega va shu yuzerga tegishli bo'lsagina space o'chadi
    @Override
    public ResponseEntity<?> deleteSpace(Long workspaceId, UUID spaceId, User user) {
        Optional<Space> optionalSpace = spaceRepository.findByIdAndOwnerIdAndWorkspaceId(spaceId, user.getId(), workspaceId);
        if (optionalSpace.isEmpty()) return status(NOT_FOUND).body("Space not found");
        Space space = optionalSpace.get();
        try {
            if (space.getAvatar() != null && attachmentRepository.existsById(space.getAvatar().getId())) {
                attachmentContentRepository.deleteByAttachmentId(space.getAvatar().getId());
            }
            spaceRepository.delete(space);
            return status(NO_CONTENT).body("Space deleted");
        } catch (Exception e) {
            return status(INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editSpace(Long workspaceId, UUID spaceId, SpaceDTO dto, User user) {
        Optional<Space> optionalSpace = spaceRepository.findById(spaceId);
        if (optionalSpace.isEmpty()) return status(NOT_FOUND).body("Space not found");
        Space space = optionalSpace.get();
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(workspaceId);
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace not found");
        space.setWorkspace(optionalWorkspace.get());
        if (Arrays.stream(AccessType.values()).toList().contains(dto.getAccessType()))
            space.setAccessType(dto.getAccessType());
        if (dto.getAvatarId() != null) {
            attachmentRepository.findById(dto.getAvatarId()).ifPresent(space::setAvatar);
        }
        space.setName(dto.getName());
        space.setColor(dto.getColor());
        return status(CREATED).body(spaceRepository.save(space));
    }
}
