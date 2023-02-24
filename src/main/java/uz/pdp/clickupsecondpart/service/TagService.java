package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.payload.TagDTO;

public interface TagService {

    ResponseEntity<?> getTag(Long tagId);

    ResponseEntity<?> getAllWorkspaceTags(Long workspaceId);

    ResponseEntity<?> addTag(TagDTO dto);

    ResponseEntity<?> editTag(Long tagId, TagDTO dto);

    ResponseEntity<?> deleteTag(Long tagId);
}
