package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.aop.CurrentUser;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.MemberDTO;
import uz.pdp.clickupsecondpart.payload.WorkspaceDTO;
import uz.pdp.clickupsecondpart.service.impl.WorkspaceServiceImpl;

import java.util.UUID;


@RestController
@RequestMapping("/api/workspace")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Workspace", description = "Workspace controller")
public class WorkspaceController {

    private final WorkspaceServiceImpl service;

    @Autowired
    public WorkspaceController(WorkspaceServiceImpl service) {
        this.service = service;
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get all my workspaces")
    @GetMapping
    public ResponseEntity<?> getWorkspaces(@CurrentUser User user) {
        return service.getAllMyWorkspace(user);
    }

    @PreAuthorize("isAuthenticated()")
    @Operation(summary = "Get workspace by id")
    @ApiResponse(responseCode = "404", description = "Workspace not found", content = @Content)
    @GetMapping(value = "/{workspaceId}")
    public ResponseEntity<?> getWorkspace(@PathVariable(name = "workspaceId") Long workspaceId) {
        return service.getOne(workspaceId);
    }

    @Operation(summary = "Create workspace")
    @PostMapping
    public ResponseEntity<?> createWorkspace(@RequestBody @Valid WorkspaceDTO workspaceDTO, @CurrentUser User user) {
        return service.create(workspaceDTO, user);
    }

    @Operation(summary = "Update workspace")
    @PutMapping("/{workspaceId}")
    public ResponseEntity<?> editWorkspace(@PathVariable(name = "workspaceId") Long id, @RequestBody @Valid WorkspaceDTO workspaceDTO) {
        return service.edit(id, workspaceDTO);
    }

    @Operation(summary = "Change user workspace")
    @PutMapping("/{workspaceId}/change/{userId}")
    public ResponseEntity<?> changeWorkspace(@PathVariable(name = "workspaceId") Long id, @PathVariable(name = "userId") UUID userId) {
        return service.changeWorkspace(id, userId);
    }

    @Operation(summary = "Add, Edit, Delete member in workspace")
    @PostMapping("/{workspaceId}/member")
    public ResponseEntity<?> addOrEditAndDeleteWorkspaceUser(@PathVariable(name = "workspaceId") Long id, @RequestBody @Valid MemberDTO memberDTO) {
        return service.addOrEditOrRemoveWorkspaceUser(id, memberDTO);
    }

    @Operation(summary = "Join workspace")
    @GetMapping("/{workspaceId}/user/{userId}")
    public ResponseEntity<?> joinWorkspace(@PathVariable(name = "workspaceId") Long id, @PathVariable(name = "userId") UUID userId) {
        return service.joinToWorkspace(id, userId);
    }

    @Operation(summary = "Delete workspace")
    @DeleteMapping("/{workspaceId}")
    public ResponseEntity<?> deleteWorkspace(@PathVariable(name = "workspaceId") Long id) {
        return service.delete(id);
    }

    @GetMapping("/{workspaceId}/member")
    public ResponseEntity<?> getWorkspaceMembers(@PathVariable(name = "workspaceId") Long id) {
        return service.getWorkspaceMembers(id);
    }
}