package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickupsecondpart.aop.CurrentUser;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.service.impl.AttachmentServiceImpl;

import java.util.UUID;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/attachment")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Attachment", description = "Operations about attachments")
public class AttachmentController {

    private final AttachmentServiceImpl service;

    @Autowired
    public AttachmentController(AttachmentServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{attachmentId}")
    @Operation(summary = "Get attachment")
    public ResponseEntity<?> getAttachment(@PathVariable(name = "attachmentId") UUID id) {
        return service.getAttachment(id);
    }

    @GetMapping("/me")
    @Operation(summary = "Get my attachments")
    public ResponseEntity<?> getMyAttachments(@CurrentUser User user) {
        return service.getMyAttachments(user.getId());
    }

    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = {APPLICATION_JSON_VALUE, "text/plain"})
    @Operation(summary = "Upload attachment")
    public ResponseEntity<?> uploadAttachment(@RequestParam("file") MultipartFile file) {
        return service.uploadAttachment(file);
    }

    @DeleteMapping("/{attachmentId}")
    @Operation(summary = "Delete attachment")
    public ResponseEntity<?> deleteAttachment(@PathVariable(name = "attachmentId") UUID id) {
        return service.deleteAttachment(id);
    }
}
