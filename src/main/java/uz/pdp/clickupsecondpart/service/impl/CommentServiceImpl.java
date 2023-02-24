package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.Comment;
import uz.pdp.clickupsecondpart.entity.Task;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.entity.enums.SystemRoleName;
import uz.pdp.clickupsecondpart.payload.CommentDTO;
import uz.pdp.clickupsecondpart.repository.CommentRepository;
import uz.pdp.clickupsecondpart.repository.TaskRepository;
import uz.pdp.clickupsecondpart.service.CommentService;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repository;

    private final TaskRepository taskRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository repository, TaskRepository taskRepository) {
        this.repository = repository;
        this.taskRepository = taskRepository;
    }

    @Override
    public ResponseEntity<?> getAllTaskComments(UUID taskId) {
        return ok(repository.findAllByTask_Id(taskId));
    }

    @Override
    public ResponseEntity<?> getComment(UUID commentId) {
        Optional<Comment> optionalComment = repository.findById(commentId);
        if (optionalComment.isEmpty()) return status(NOT_FOUND).body("Comment not found");
        return ok(optionalComment.get());
    }

    @Override
    public ResponseEntity<?> addComment(CommentDTO commentDTO) {
        Optional<Task> optionalTask = taskRepository.findById(commentDTO.getTaskId());
        if (optionalTask.isEmpty()) return status(NOT_FOUND).body("Task not found");
        Comment save = repository.save(new Comment(commentDTO.getText(), optionalTask.get()));
        return status(CREATED).body(save);
    }

    @Override
    public ResponseEntity<?> editComment(UUID commentId, CommentDTO commentDTO) {
        Optional<Comment> optionalComment = repository.findById(commentId);
        if (optionalComment.isEmpty()) return status(NOT_FOUND).body("Comment not found");
        Comment comment = optionalComment.get();
        comment.setText(commentDTO.getText());
        comment = repository.save(comment);
        return status(CREATED).body(comment);
    }

    @Override
    public ResponseEntity<?> deleteComment(UUID commentId, User user) {
        Optional<Comment> optionalComment = repository.findById(commentId);
        if (optionalComment.isEmpty()) return status(NOT_FOUND).body("Comment not found");
        if (user.getSystemRoleName().equals(SystemRoleName.SYSTEM_ADMIN) || optionalComment.get().getId().equals(user.getId())) {
            try {
                repository.deleteById(commentId);
                status(NO_CONTENT).body("Comment deleted successfully");
            } catch (Exception e) {
                return status(INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
            }
        }
        return badRequest().body("You are not allowed to expand this comment");
    }
}
