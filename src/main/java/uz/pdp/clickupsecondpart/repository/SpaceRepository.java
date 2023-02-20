package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.Space;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpaceRepository extends JpaRepository<Space, UUID> {
     List<Space> findAllByWorkspace_IdAndOwner_Id(Long workspace_id, UUID owner_id);

     boolean existsByNameAndWorkspaceId(String name, Long workspace_id);

//     boolean existsByIdAndOwnerIdAndWorkspaceId(UUID id, UUID owner_id, Long workspace_id);

     Optional<Space> findByIdAndOwnerIdAndWorkspaceId(UUID id, UUID owner_id, Long workspace_id);
}
