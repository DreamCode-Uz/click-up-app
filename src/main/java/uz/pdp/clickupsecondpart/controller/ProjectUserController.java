package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.aop.CurrentUser;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.ProjectUserDTO;
import uz.pdp.clickupsecondpart.service.ProjectUserService;
import uz.pdp.clickupsecondpart.service.impl.ProjectUserServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/projectuser")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Project User", description = "Project User Controller")
public class ProjectUserController implements ProjectUserService {

    private final ProjectUserServiceImpl service;

    @Autowired
    public ProjectUserController(ProjectUserServiceImpl service) {
        this.service = service;
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @Operation(summary = "Create Project User")
    @Override
    public ResponseEntity<?> addProjectUser(@RequestBody ProjectUserDTO dto) {
        return service.addProjectUser(dto);
    }

    @PutMapping(value = "/{projectUserId}/user/{userId}", consumes = "application/json", produces = "application/json")
    @Operation(summary = "Update Project User")
    @Override
    public ResponseEntity<?> editProjectUser(@PathVariable UUID projectUserId, @PathVariable UUID userId, @CurrentUser User user) {
        return service.editProjectUser(projectUserId, userId, user);
    }

    @DeleteMapping("/{projectUserId}/user/{projectId}")
    @Operation(summary = "Delete Project User")
    @Override
    public ResponseEntity<?> deleteProjectUser(@PathVariable UUID projectUserId, @PathVariable UUID projectId, @CurrentUser User user) {
        return service.deleteProjectUser(projectUserId, projectId, user);
    }
}
