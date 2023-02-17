package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AttachmentService {

    ResponseEntity<?> getMyAttachments(UUID userId);

    ResponseEntity<?> getAttachment(UUID attachmentId);

    ResponseEntity<?> uploadAttachment(MultipartFile file);

    ResponseEntity<?> deleteAttachment(UUID attachmentId);
}
