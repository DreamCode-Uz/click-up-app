package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickupsecondpart.entity.Attachment;
import uz.pdp.clickupsecondpart.entity.AttachmentContent;
import uz.pdp.clickupsecondpart.repository.AttachmentContentRepository;
import uz.pdp.clickupsecondpart.repository.AttachmentRepository;
import uz.pdp.clickupsecondpart.service.AttachmentService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class AttachmentServiceImpl implements AttachmentService {

    private final AttachmentRepository attachmentRepository;

    private final AttachmentContentRepository contentRepository;

    @Autowired
    public AttachmentServiceImpl(AttachmentRepository attachmentRepository, AttachmentContentRepository contentRepository) {
        this.attachmentRepository = attachmentRepository;
        this.contentRepository = contentRepository;
    }


    @Override
    public ResponseEntity<?> getMyAttachments(UUID userId) {
        List<Attachment> attachments = attachmentRepository.findAllByCreatedBy(userId);
        return ok(attachments);
    }

    @Override
    public ResponseEntity<?> getAttachment(UUID attachmentId) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(attachmentId);
        if (optionalAttachment.isEmpty()) return status(NOT_FOUND).body("Attachment not found");
        return ok(optionalAttachment.get());
    }

    @Override
    public ResponseEntity<?> uploadAttachment(MultipartFile file) {
        if (file.isEmpty()) {
            return status(BAD_REQUEST).body("File not empty");
        }
        Attachment attachment = new Attachment(
                file.getName(),
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize()
        );

        try {
            AttachmentContent content = new AttachmentContent(
                    attachment,
                    file.getBytes()
            );
            AttachmentContent save = contentRepository.save(content);
            return status(CREATED).body(save.getAttachment());
        } catch (IOException e) {
            return status(SERVICE_UNAVAILABLE).body("Error uploading file");
        }
    }

    @Override
    public ResponseEntity<?> deleteAttachment(UUID attachmentId) {
        Optional<AttachmentContent> optionalAttachmentContent = contentRepository.findByAttachment_Id(attachmentId);
        if (optionalAttachmentContent.isEmpty()) return status(NOT_FOUND).body("Attachment not found");
        try {
            contentRepository.delete(optionalAttachmentContent.get());
            return status(NO_CONTENT).body("Attachment deleted");
        } catch (Exception e) {
            return status(INTERNAL_SERVER_ERROR).body("Error deleting file");
        }
    }
}
