package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.ProjectDTO;

import java.util.UUID;

public interface ProjectService {

    ResponseEntity<?> getAllProjects(UUID spaceId);

    ResponseEntity<?> getOneProject(UUID spaceId, UUID projectId);

    ResponseEntity<?> addProject(ProjectDTO dto, User user);

    ResponseEntity<?> editProject(UUID projectId, ProjectDTO dto, User user);

    ResponseEntity<?> deleteProject(UUID projectId, User user);
}
