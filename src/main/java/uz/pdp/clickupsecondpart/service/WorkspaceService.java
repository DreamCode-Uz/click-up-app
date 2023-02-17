package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.MemberDTO;
import uz.pdp.clickupsecondpart.payload.WorkspaceDTO;

import java.util.UUID;

public interface WorkspaceService {

    String WORKSPACE_JOINED_URL = "http://localhost:8080/api/workspace/%s/user/%s";

    ResponseEntity<?> getAll();

    ResponseEntity<?> getOne(Long workspaceId);

    ResponseEntity<?> create(WorkspaceDTO dto, User user);

    ResponseEntity<?> edit(Long workspaceId, WorkspaceDTO dto);

    ResponseEntity<?> changeWorkspace(Long workspaceId, UUID userId);

    ResponseEntity<?> addOrEditOrRemoveWorkspaceUser(Long workspaceId, MemberDTO dto);

    ResponseEntity<?> joinToWorkspace(Long workspaceId, UUID userId);

    ResponseEntity<?> delete(Long workspaceId);
}
