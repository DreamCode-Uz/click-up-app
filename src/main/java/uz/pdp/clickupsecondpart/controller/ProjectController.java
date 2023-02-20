package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.aop.CurrentUser;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.ProjectDTO;
import uz.pdp.clickupsecondpart.service.ProjectService;
import uz.pdp.clickupsecondpart.service.impl.ProjectServiceImpl;

import java.util.UUID;

@RestController
@RequestMapping("/api/project")
@Tag(name = "Project", description = "Project API")
@SecurityRequirement(name = "bearerAuth")
public class ProjectController implements ProjectService {

    private final ProjectServiceImpl service;

    @Autowired
    public ProjectController(ProjectServiceImpl service) {
        this.service = service;
    }

    @GetMapping(value = "/space/{spaceId}", produces = {"application/json", "text/plain"})
    @Operation(summary = "Get all projects in a spaceId")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content)
    @Override
    public ResponseEntity<?> getAllProjects(@PathVariable UUID spaceId) {
        return service.getAllProjects(spaceId);
    }

    @GetMapping(value = "{projectId}/space/{spaceId}", produces = {"application/json", "text/plain"})
    @Operation(summary = "Get project by spaceId")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content)
    @Override
    public ResponseEntity<?> getOneProject(@PathVariable UUID spaceId, @PathVariable UUID projectId) {
        return service.getOneProject(spaceId, projectId);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = "text/plain")
    @Operation(summary = "Add new project")
    @ApiResponse(responseCode = "200", description = "Success", content = @Content)
    @Override
    public ResponseEntity<?> addProject(ProjectDTO dto, @CurrentUser User user) {
        return service.addProject(dto, user);
    }

    @PutMapping(value = "/{projectId}", produces = {"application/json", "text/plain"})
    @Operation(summary = "Edit project by spaceId")
    @ApiResponse(responseCode = "200", content = @Content)
    @Override
    public ResponseEntity<?> editProject(@PathVariable UUID projectId, @RequestBody ProjectDTO dto, @CurrentUser User user) {
        return service.editProject(projectId, dto, user);
    }

    @DeleteMapping("/{projectId}")
    @Operation(summary = "Delete project by spaceId")
    @ApiResponse(responseCode = "200", content = @Content)
    @Override
    public ResponseEntity<?> deleteProject(@PathVariable UUID projectId, @CurrentUser User user) {
        return service.deleteProject(projectId, user);
    }
}
