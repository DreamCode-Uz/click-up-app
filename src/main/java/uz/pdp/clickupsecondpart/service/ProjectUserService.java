package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.ProjectUserDTO;

import java.util.UUID;

public interface ProjectUserService {

    ResponseEntity<?> addProjectUser(ProjectUserDTO dto);

    ResponseEntity<?> editProjectUser(UUID projectUserId, UUID userId, User user);

    ResponseEntity<?> deleteProjectUser(UUID projectUserId, UUID projectId, User user);
}
