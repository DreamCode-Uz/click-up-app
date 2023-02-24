package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.pdp.clickupsecondpart.entity.Category;
import uz.pdp.clickupsecondpart.entity.Priority;
import uz.pdp.clickupsecondpart.entity.Status;
import uz.pdp.clickupsecondpart.entity.Task;
import uz.pdp.clickupsecondpart.payload.TaskDTO;
import uz.pdp.clickupsecondpart.repository.*;
import uz.pdp.clickupsecondpart.service.TaskService;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    private final TaskTagRepository taskTagRepository;

    private final CategoryRepository categoryRepository;

    private final PriorityRepository priorityRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, TaskTagRepository taskTagRepository, CategoryRepository categoryRepository, PriorityRepository priorityRepository,
                           StatusRepository statusRepository) {
        this.taskRepository = taskRepository;
        this.taskTagRepository = taskTagRepository;
        this.categoryRepository = categoryRepository;
        this.priorityRepository = priorityRepository;
        this.statusRepository = statusRepository;
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
        return null;
    }

    @Override
    public ResponseEntity<?> removeTask(UUID taskId) {
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        if (optionalTask.isEmpty()) return status(NOT_FOUND).body("Task not found");
        return null;
    }

    @Override
    public ResponseEntity<?> previewTaskAttach(UUID taskId, UUID attachmentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> uploadTaskAttachment(UUID taskId, MultipartFile file) {
        return null;
    }

    @Override
    public ResponseEntity<?> removeTaskAttachment(UUID attachmentId) {
        return null;
    }

    @Override
    public ResponseEntity<?> addTaskTag(UUID taskId, Long tagId) {
        return null;
    }
}
