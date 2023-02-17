package uz.pdp.clickupsecondpart.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.payload.SpaceDTO;
import uz.pdp.clickupsecondpart.repository.SpaceRepository;
import uz.pdp.clickupsecondpart.repository.WorkspaceRepository;
import uz.pdp.clickupsecondpart.service.SpaceService;

import java.util.UUID;

@Service
public class SpaceServiceImpl implements SpaceService {

    private final SpaceRepository spaceRepository;

    private final WorkspaceRepository workspaceRepository;

    @Autowired
    public SpaceServiceImpl(SpaceRepository spaceRepository, WorkspaceRepository workspaceRepository) {
        this.spaceRepository = spaceRepository;
        this.workspaceRepository = workspaceRepository;
    }


    @Override
    public ResponseEntity<?> getAllSpaces(Long workspaceId) {
        return null;
    }

    @Override
    public ResponseEntity<?> getSpace(UUID spaceId) {
        return null;
    }

    @Override
    public ResponseEntity<?> createSpace(Long workspaceId, SpaceDTO dto, User user) {
        return null;
    }

    @Override
    public ResponseEntity<?> deleteSpace(Long workspaceId, UUID spaceId, User user) {
        return null;
    }

    @Override
    public ResponseEntity<?> editSpace(Long workspaceId, UUID spaceId, SpaceDTO dto, User user) {
        return null;
    }
}
