package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickupsecondpart.repository.AttachmentContentRepository;
import uz.pdp.clickupsecondpart.repository.AttachmentRepository;
import uz.pdp.clickupsecondpart.service.AttachmentService;

import java.util.UUID;

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

        return null;
    }

    @Override
    public ResponseEntity<?> getAttachment(UUID attachmentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> uploadAttachment(MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteAttachment(UUID attachmentId) {
        return null;
    }
}
