package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.CommentDTO;

import java.util.UUID;

public interface CommentService {

    ResponseEntity<?> getAllTaskComments(UUID taskId);

    ResponseEntity<?> getComment(UUID commentId);

    ResponseEntity<?> addComment(CommentDTO commentDTO);

    ResponseEntity<?> editComment(UUID commentId, CommentDTO commentDTO);

    ResponseEntity<?> deleteComment(UUID commentId, User user);
}
