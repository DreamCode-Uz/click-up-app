package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.Tag;
import uz.pdp.clickupsecondpart.entity.Workspace;
import uz.pdp.clickupsecondpart.payload.TagDTO;
import uz.pdp.clickupsecondpart.repository.TagRepository;
import uz.pdp.clickupsecondpart.repository.WorkspaceRepository;
import uz.pdp.clickupsecondpart.service.TagService;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class TagServiceImpl implements TagService {

    private final WorkspaceRepository workspaceRepository;
    private final TagRepository tagRepository;

    @Autowired
    public TagServiceImpl(WorkspaceRepository workspaceRepository, TagRepository tagRepository) {
        this.workspaceRepository = workspaceRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public ResponseEntity<?> getTag(Long tagId) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isEmpty()) return status(NOT_FOUND).body("Tag not found");
        return ok(optionalTag.get());
    }

    @Override
    public ResponseEntity<?> getAllWorkspaceTags(Long workspaceId) {
        if (!workspaceRepository.existsById(workspaceId)) return status(NOT_FOUND).body("workspace does not exist");
        return ok(tagRepository.findAllByWorkspace_Id(workspaceId));
    }

    @Override
    public ResponseEntity<?> addTag(TagDTO dto) {
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace does not exist");
        if (tagRepository.existsByWorkspace_IdAndName(dto.getWorkspaceId(), dto.getName()))
            return status(UNPROCESSABLE_ENTITY).body("Tag already exists");
        Tag save = tagRepository.save(new Tag(dto.getName(), dto.getColor(), optionalWorkspace.get()));
        return ResponseEntity.status(CREATED).body(save);
    }

    @Override
    public ResponseEntity<?> editTag(Long tagId, TagDTO dto) {
        Optional<Tag> optionalTag = tagRepository.findById(tagId);
        if (optionalTag.isEmpty()) return status(NOT_FOUND).body("Tag not found");
        Optional<Workspace> optionalWorkspace = workspaceRepository.findById(dto.getWorkspaceId());
        if (optionalWorkspace.isEmpty()) return status(NOT_FOUND).body("Workspace not found");
        if (tagRepository.existsByWorkspace_IdAndNameAndIdNot(dto.getWorkspaceId(), dto.getName(), optionalTag.get().getId()))
            return status(UNPROCESSABLE_ENTITY).body("Tag already exists another column");
        Tag tag = optionalTag.get();
        tag.setName(dto.getName());
        tag.setColor(dto.getColor());
        tag.setWorkspace(optionalWorkspace.get());
        return status(CREATED).body(tagRepository.save(tag));
    }

    @Override
    public ResponseEntity<?> deleteTag(Long tagId) {
        if (tagRepository.existsById(tagId)) return status(NOT_FOUND).body("Tag does not exist");
        try {
            tagRepository.deleteById(tagId);
            return status(NO_CONTENT).body("Tag successfully deleted");
        } catch (Exception e) {
            return status(INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
