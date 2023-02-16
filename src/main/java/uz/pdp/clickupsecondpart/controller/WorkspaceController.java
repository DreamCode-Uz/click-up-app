package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.aop.CurrentUser;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.WorkspaceDTO;
import uz.pdp.clickupsecondpart.service.impl.WorkspaceServiceImpl;

import java.util.UUID;


@RestController
@RequestMapping("/api/workspace")
@SecurityRequirement(name = "bearerAuth")
public class WorkspaceController {

    private final WorkspaceServiceImpl service;

    @Autowired
    public WorkspaceController(WorkspaceServiceImpl service) {
        this.service = service;
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all workspaces", tags = "workspace")
    @GetMapping
    public ResponseEntity<?> getWorkspaces() {
        return service.getAll();
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get workspace by id", tags = "workspace")
    @ApiResponse(responseCode = "404", description = "Workspace not found", content = @Content)
    @GetMapping(value = "/{workspaceId}")
    public ResponseEntity<?> getWorkspace(@PathVariable(name = "workspaceId") Long workspaceId) {
        return service.getOne(workspaceId);
    }

    @Operation(summary = "Create workspace", tags = "workspace")
    @PostMapping
    public ResponseEntity<?> createWorkspace(@RequestBody @Valid WorkspaceDTO workspaceDTO, @CurrentUser User user) {
        return service.create(workspaceDTO, user);
    }

    @Operation(summary = "Update workspace", tags = "workspace")
    @PutMapping("/{workspaceId}")
    public ResponseEntity<?> editWorkspace(@PathVariable(name = "workspaceId") Long id, @RequestBody @Valid WorkspaceDTO workspaceDTO) {
        return service.edit(id, workspaceDTO);
    }

    @Operation(summary = "Change user workspace", tags = "workspace")
    @PutMapping("/{workspaceId}/change")
    public ResponseEntity<?> changeWorkspace(@PathVariable(name = "workspaceId") Long id, @RequestParam(name = "userId") UUID userId) {
        return service.changeWorkspace(id, userId);
    }

    @Operation(summary = "Delete workspace", tags = "workspace")
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<?> deleteWorkspace(@PathVariable(name = "workspaceId") Long id) {
        return service.delete(id);
    }
}
