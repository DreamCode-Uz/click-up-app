package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.Workspace;

import java.util.List;
import java.util.UUID;

@Repository
public interface WorkspaceRepository extends JpaRepository<Workspace, Long> {

    boolean existsByOwner_IdAndName(UUID owner_id, String name);

    List<Workspace> findAllByOwner_Id(UUID owner_id);
}
