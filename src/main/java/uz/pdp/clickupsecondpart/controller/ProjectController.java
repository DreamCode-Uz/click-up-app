package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.ProjectDTO;
import uz.pdp.clickupsecondpart.service.ProjectService;
import uz.pdp.clickupsecondpart.service.impl.ProjectServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/project")
@Tag(name = "Project", description = "Project API")
public class ProjectController implements ProjectService {

    private final ProjectServiceImpl service;

    @Autowired
    public ProjectController(ProjectServiceImpl service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<?> getAllProjects(UUID spaceId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getOneProject(UUID spaceId, UUID projectId) {
        return null;
    }

    @Override
    public ResponseEntity<?> addProject(ProjectDTO dto, User user) {
        return null;
    }

    @Override
    public ResponseEntity<?> editProject(UUID projectId, ProjectDTO dto, User user) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteProject(UUID projectId, User user) {
        return null;
    }
}
