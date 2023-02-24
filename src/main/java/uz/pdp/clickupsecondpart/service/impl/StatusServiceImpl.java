package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.Category;
import uz.pdp.clickupsecondpart.entity.Project;
import uz.pdp.clickupsecondpart.entity.Status;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.StatusDTO;
import uz.pdp.clickupsecondpart.repository.CategoryRepository;
import uz.pdp.clickupsecondpart.repository.ProjectRepository;
import uz.pdp.clickupsecondpart.repository.StatusRepository;
import uz.pdp.clickupsecondpart.service.StatusService;

import java.util.Optional;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@Service
public class StatusServiceImpl implements StatusService {

    private final StatusRepository statusRepository;

    private final CategoryRepository categoryRepository;

    private final ProjectRepository projectRepository;

    @Autowired
    public StatusServiceImpl(StatusRepository statusRepository, CategoryRepository categoryRepository, ProjectRepository projectRepository) {
        this.statusRepository = statusRepository;
        this.categoryRepository = categoryRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public ResponseEntity<?> getStatus() {
        return null;
    }

    @Override
    public ResponseEntity<?> getStatus(Long statusId) {
        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        if (optionalStatus.isEmpty()) return status(NOT_FOUND).body("Status not found");
        return ok(optionalStatus.get());
    }

    @Override
    public ResponseEntity<?> addStatus(StatusDTO dto, User user) {
        Optional<Category> optionalCategory = categoryRepository.findById(dto.getCategoryId());
        if (optionalCategory.isEmpty()) return status(NOT_FOUND).body("Category not found");
        Optional<Project> optionalProject = projectRepository.findById(dto.getProjectId());
        if (optionalProject.isEmpty()) return status(NOT_FOUND).body("Project not found");
        if (statusRepository.existsByNameAndCategory_Id(dto.getName(), dto.getCategoryId()))
            return status(UNPROCESSABLE_ENTITY).body("Status name already exists");
        Status status = new Status(dto.getName(), dto.getColor(), dto.getStatus(), user, optionalProject.get(), optionalCategory.get());
        return status(CREATED).body(statusRepository.save(status));
    }

    @Override
    public ResponseEntity<?> updateStatus(Long statusId, StatusDTO dto, User user) {
        Optional<Status> optionalStatus = statusRepository.findById(statusId);
        if (optionalStatus.isEmpty()) return status(NOT_FOUND).body("Status not found");
        if (statusRepository.existsByNameAndCategory_IdAndIdNot(dto.getName(), dto.getCategoryId(), statusId))
            return status(UNPROCESSABLE_ENTITY).body("Status already exists");
        Status status = optionalStatus.get();
        status.setName(dto.getName());
        status.setColor(dto.getColor());
        statusRepository.save(status);
        return status(CREATED).body("Created successfully");
    }

    @Override
    public ResponseEntity<?> deleteStatus(Long statusId, User user) {
        if (statusRepository.existsById(statusId)) {
            if (statusRepository.existsByIdAndCreatedBy(statusId, user.getId())) {
                try {
                    statusRepository.deleteById(statusId);
                } catch (Exception e) {
                    return status(INTERNAL_SERVER_ERROR).body("Status not deleted");
                }
                return status(NO_CONTENT).body("Deleted successfully");
            } else {
                return badRequest().body("You cannot delete this status");
            }
        }
        return status(NOT_FOUND).body("Status not found");
    }
}
