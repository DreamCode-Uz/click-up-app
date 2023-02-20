package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.Project;
import uz.pdp.clickupsecondpart.entity.Space;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.ProjectDTO;
import uz.pdp.clickupsecondpart.repository.ProjectRepository;
import uz.pdp.clickupsecondpart.repository.SpaceRepository;
import uz.pdp.clickupsecondpart.service.ProjectService;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.status;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    private final SpaceRepository spaceRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, SpaceRepository spaceRepository) {
        this.projectRepository = projectRepository;
        this.spaceRepository = spaceRepository;
    }

    @Override
    public ResponseEntity<?> getAllProjects(UUID spaceId) {
        if (spaceId != null && !spaceRepository.existsById(spaceId))
            return status(NOT_FOUND).body("Space not found");
        return ok(projectRepository.findAllBySpaceId(spaceId));
    }

    @Override
    public ResponseEntity<?> getOneProject(UUID spaceId, UUID projectId) {
        if (!spaceRepository.existsById(spaceId))
            return status(NOT_FOUND).body("Space not found");
        Optional<Project> optionalProject = projectRepository.findByIdAndSpaceId(projectId, spaceId);
        if (optionalProject.isEmpty()) return status(NOT_FOUND).body("Project not found");
        return ok(optionalProject.get());
    }

    @Override
    public ResponseEntity<?> addProject(ProjectDTO dto, User user) {
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (optionalSpace.isEmpty()) return status(NOT_FOUND).body("Space not found");
        Project project = new Project(
                dto.getName(),
                dto.getColor(),
                false,
                optionalSpace.get()
        );
        project = projectRepository.save(project);
        return status(CREATED).body(project);
    }

    @Override
    public ResponseEntity<?> editProject(UUID projectId, ProjectDTO dto, User user) {
        Optional<Project> optionalProject = projectRepository.findById(projectId);
        if (optionalProject.isEmpty()) return status(NOT_FOUND).body("Project not found");
        Optional<Space> optionalSpace = spaceRepository.findById(dto.getSpaceId());
        if (optionalSpace.isEmpty()) return status(NOT_FOUND).body("Space not found");
        Project project = optionalProject.get();
        project.setName(dto.getName());
        project.setArchived(dto.isArchived());
        project.setColor(dto.getColor());
        project.setSpace(optionalSpace.get());
        return status(CREATED).body(projectRepository.save(project));
    }

    @Override
    public ResponseEntity<?> deleteProject(UUID projectId, User user) {
        if (!projectRepository.existsById(projectId)) return status(NOT_FOUND).body("Project not found");
        try {
            projectRepository.deleteById(projectId);
            return status(NO_CONTENT).body("Project deleted");
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return status(INTERNAL_SERVER_ERROR).body("Project not deleted");
    }
}
