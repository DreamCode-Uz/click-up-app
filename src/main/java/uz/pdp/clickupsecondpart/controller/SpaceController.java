package uz.pdp.clickupsecondpart.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.aop.CurrentUser;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.SpaceDTO;
import uz.pdp.clickupsecondpart.service.impl.SpaceServiceImpl;

import java.util.UUID;


@RestController
@RequestMapping("/api/space")
@SecurityRequirement(name = "bearerAuth")
@Tag(name = "Space", description = "Space related operations")
public class SpaceController {

    private final SpaceServiceImpl spaceService;

    @Autowired
    public SpaceController(SpaceServiceImpl spaceService) {
        this.spaceService = spaceService;
    }

    @GetMapping("/workspace/{workspaceId}")
    @Operation(summary = "Get all spaces")
    public ResponseEntity<?> getAllSpaces(@PathVariable Long workspaceId, @CurrentUser User user) {
        return spaceService.getAllSpaces(workspaceId, user);
    }

    @GetMapping("/{spaceId}")
    @Operation(summary = "Get a space")
    public ResponseEntity<?> getSpace(@PathVariable UUID spaceId) {
        return spaceService.getSpace(spaceId);
    }

    @PostMapping(value = "/workspace/{workspaceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Create a space")
    public ResponseEntity<?> createSpace(@PathVariable(name = "workspaceId") Long workspaceId, @RequestBody @Valid SpaceDTO spaceDTO, @CurrentUser User user) {
        return spaceService.createSpace(workspaceId, spaceDTO, user);
    }

    @PutMapping(value = "/{spaceId}/workspace/{workspaceId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @Operation(summary = "Update a space")
    public ResponseEntity<?> editSpace(@PathVariable(name = "spaceId") UUID spaceId, @PathVariable(name = "workspaceId") Long workspaceId, @RequestBody @Valid SpaceDTO spaceDTO, @CurrentUser User user) {
        return spaceService.editSpace(workspaceId, spaceId, spaceDTO, user);
    }

    @DeleteMapping(value = "/{spaceId}/workspace/{workspaceId}")
    @Operation(summary = "Delete a space")
    public ResponseEntity<?> deleteSpace(@PathVariable(name = "spaceId") UUID spaceId, @PathVariable(name = "workspaceId") Long workspaceId, @CurrentUser User user) {
        return spaceService.deleteSpace(workspaceId, spaceId, user);
    }
}
