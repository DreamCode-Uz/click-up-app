package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.WorkspacePermission;

@Repository
public interface WorkspacePermissionRepository extends JpaRepository<WorkspacePermission, Long> {

}
