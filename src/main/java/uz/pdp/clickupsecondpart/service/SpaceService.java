package uz.pdp.clickupsecondpart.service;

import org.springframework.http.ResponseEntity;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.SpaceDTO;

import java.util.UUID;

public interface SpaceService {

    ResponseEntity<?> getAllSpaces(Long workspaceId, User user);

    ResponseEntity<?> getSpace(UUID spaceId);

    ResponseEntity<?> createSpace(Long workspaceId, SpaceDTO dto, User user);

    ResponseEntity<?> deleteSpace(Long workspaceId, UUID spaceId, User user);

    ResponseEntity<?> editSpace(Long workspaceId, UUID spaceId, SpaceDTO dto, User user);
}
