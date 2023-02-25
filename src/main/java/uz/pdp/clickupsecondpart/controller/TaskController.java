package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickupsecondpart.payload.TaskDTO;
import uz.pdp.clickupsecondpart.service.TaskService;
import uz.pdp.clickupsecondpart.service.impl.TaskServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/task")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Task Controller")
public class TaskController implements TaskService {

    private final TaskServiceImpl service;

    @Autowired
    public TaskController(TaskServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/{taskId}")
    @Override
    public ResponseEntity<?> getTask(@PathVariable UUID taskId) {
        return null;
    }

    @GetMapping("/{categoryId}")
    @Override
    public ResponseEntity<?> getAllTasks(@PathVariable UUID categoryId) {
        return null;
    }

    @PostMapping
    @Override
    public ResponseEntity<?> addTask(@RequestBody TaskDTO dto) {
        return null;
    }

    @PutMapping("/{taskId}")
    @Override
    public ResponseEntity<?> updateTask(@PathVariable UUID taskId, @RequestBody @Valid TaskDTO dto) {
        return service.updateTask(taskId, dto);
    }

    @DeleteMapping("/{taskId}")
    @Override
    public ResponseEntity<?> removeTask(@PathVariable UUID taskId) {
        return null;
    }

    //    Yuklab olish uchun emas ko'rish uchun rasm beriladi
    @GetMapping("/{taskId}/attachment/preview/{attachmentId}")
    @Override
    public ResponseEntity<?> previewTaskAttach(@PathVariable UUID taskId, @PathVariable UUID attachmentId) {
        return service.previewTaskAttach(taskId, attachmentId);
    }

    @PostMapping(value = "/{taskId}/attachment", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @Override
    public ResponseEntity<?> uploadTaskAttachment(@PathVariable UUID taskId, @RequestParam("file") MultipartFile file) {
        return service.uploadTaskAttachment(taskId, file);
    }

    @DeleteMapping("/{taskId}/attachment/{attachmentId}")
    @Override
    public ResponseEntity<?> removeTaskAttachment(@PathVariable UUID taskId, @PathVariable UUID attachmentId) {
        return service.removeTaskAttachment(taskId, attachmentId);
    }

    @Override
    public ResponseEntity<?> addTaskTag(@PathVariable UUID taskId, @PathVariable Long tagId) {
        return service.addTaskTag(taskId, tagId);
    }
}
