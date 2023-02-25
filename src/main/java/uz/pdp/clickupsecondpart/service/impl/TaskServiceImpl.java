package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import uz.pdp.clickupsecondpart.entity.*;
import uz.pdp.clickupsecondpart.payload.TaskDTO;
import uz.pdp.clickupsecondpart.repository.*;
import uz.pdp.clickupsecondpart.service.TaskService;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;
import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskTagRepository taskTagRepository;

    private final CategoryRepository categoryRepository;

    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;
    private final AttachmentContentRepository attachmentContentRepository;
    private final TaskAttachmentRepository taskAttachmentRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskTagRepository taskTagRepository, CategoryRepository categoryRepository, PriorityRepository priorityRepository,
                           StatusRepository statusRepository,
                           AttachmentContentRepository attachmentContentRepository,
                           TaskAttachmentRepository taskAttachmentRepository) {
        this.taskRepository = taskRepository;
        this.taskTagRepository = taskTagRepository;
        this.categoryRepository = categoryRepository;
        this.priorityRepository = priorityRepository;
        this.statusRepository = statusRepository;
        this.attachmentContentRepository = attachmentContentRepository;
        this.taskAttachmentRepository = taskAttachmentRepository;
    }

    @Override
    public ResponseEntity<?> getTask(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) return status(NOT_FOUND).body("Task not found");
        return ok(optionalTask.get());
    }

    @Override
    public ResponseEntity<?> getAllTasks(UUID categoryId) {
        return ok(taskRepository.findAllByCategory_Id(categoryId));
    }

    @Override
    public ResponseEntity<?> addTask(TaskDTO dto) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()) return status(NOT_FOUND).body("Category not found");
        Optional<Priority> optionalPriority = priorityRepository.findById(dto.getPriorityId());
        if (optionalPriority.isEmpty()) return status(NOT_FOUND).body("Priority not found");
        Optional<Status> optionalStatus = statusRepository.findById(dto.getStatusId());
        if (optionalStatus.isEmpty()) return status(NOT_FOUND).body("Status not found");
        Task parentTask = null;
        if (dto.getParentTask() != null && taskRepository.existsById(dto.getParentTask())) {
            parentTask = taskRepository.getReferenceById(dto.getParentTask());
        }
        Task task = new Task(
                dto.getName(),
                dto.getDescription(),
                Timestamp.from(new Date().toInstant()),
                Timestamp.from(dto.getDueDate().toInstant()),
                dto.getEstimateTime(),
                Timestamp.from(dto.getActiveDate().toInstant()),
                optionalStatus.get(),
                optionalCategory.get(),
                optionalPriority.get(),
                parentTask
        );
        task = taskRepository.save(task);
        return status(CREATED).body(task);
    }

    @Override
    public ResponseEntity<?> updateTask(UUID taskId, TaskDTO dto) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) return status(NOT_FOUND).body("Task not found");
        Task task = optionalTask.get();
        task.setName(dto.getName());
        task.setDescription(dto.getDescription());
        task.setEstimateTime(dto.getEstimateTime());
        task.setDueTimeHas(Timestamp.from(dto.getDueDate().toInstant()));
        priorityRepository.findById(dto.getPriorityId()).ifPresent(task::setPriority);
        statusRepository.findById(dto.getStatusId()).ifPresent(task::setStatus);
        taskRepository.save(task);
        return status(CREATED).body("Task successfully edited.");
    }

    @Override
    public ResponseEntity<?> removeTask(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) return status(NOT_FOUND).body("Task not found");
        try {
            taskRepository.delete(optionalTask.get());
        } catch (Exception e) {
            return status(INTERNAL_SERVER_ERROR).body("Task not deleted");
        }
        return status(NO_CONTENT).body("Task successfully deleted");
    }

    @Override
    public ResponseEntity<?> previewTaskAttach(UUID taskId, UUID attachmentId) {
        Optional<TaskAttachment> optionalTaskAttachment = taskAttachmentRepository.findByTask_IdAndAttachment_Id(taskId, attachmentId);
        if (optionalTaskAttachment.isEmpty()) return status(NOT_FOUND).body("Attachment not found");
        Optional<AttachmentContent> oac = attachmentContentRepository.findByAttachment_Id(attachmentId);
        if (oac.isEmpty()) return status(NOT_FOUND).body("Attachment not found");
        AttachmentContent attachmentContent = oac.get();
        return status(OK)
                .header(HttpHeaders.CONTENT_DISPOSITION, "filename=\"" + attachmentContent.getAttachment().getOriginalName() + "\"")
                .contentLength(attachmentContent.getAttachment().getSize())
                .contentType(MediaType.parseMediaType(attachmentContent.getAttachment().getContentType()))
                .body(new ByteArrayResource(attachmentContent.getContent()));
    }

    @Override
    public ResponseEntity<?> uploadTaskAttachment(UUID taskId, MultipartFile file) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) return status(NOT_FOUND).body("Task not found");
        if (file.isEmpty()) return status(BAD_REQUEST).body("File is empty");
        try {
            AttachmentContent content = new AttachmentContent(
                    new Attachment(file.getName(), file.getOriginalFilename(), file.getContentType(), file.getSize()),
                    file.getBytes()
            );
            content = attachmentContentRepository.save(content);
            taskAttachmentRepository.save(new TaskAttachment(optionalTask.get(), content.getAttachment()));
            String previewUrl = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/api/task")
                    .path(format("/{%s}/attachment/preview/{%s}", optionalTask.get().getId(), content.getAttachment().getId()))
                    .toUriString();
            return status(CREATED).body("Attachment successfully uploaded: \nPreviewUrl= " + previewUrl);
        } catch (IOException e) {
            return status(INTERNAL_SERVER_ERROR).body("Attachment not uploaded");
        }
    }

    @Override
    public ResponseEntity<?> removeTaskAttachment(UUID taskId, UUID attachmentId) {
        Optional<TaskAttachment> optionalTaskAttachment = taskAttachmentRepository.findByTask_IdAndAttachment_Id(taskId, attachmentId);
        if (optionalTaskAttachment.isEmpty()) return status(NOT_FOUND).body("Attachment not found");
        TaskAttachment taskAttachment = optionalTaskAttachment.get();
        try {
            taskAttachmentRepository.delete(taskAttachment);
            attachmentContentRepository.deleteByAttachmentId(attachmentId);
        } catch (Exception e) {
            taskAttachmentRepository.save(taskAttachment);
            return status(INTERNAL_SERVER_ERROR).body("Attachment not deleted");
        }
        return status(NO_CONTENT).body("Attachment successfully deleted");
    }

    @Override
    public ResponseEntity<?> addTaskTag(UUID taskId, Long tagId) {
        return null;
    }
}
