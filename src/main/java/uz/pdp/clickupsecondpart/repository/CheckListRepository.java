package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.CheckList;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, UUID> {

    List<CheckList> findAllByTask_Id(UUID task_id);

    Optional<CheckList> findByTask_IdAndId(UUID task_id, UUID id);

    boolean existsByIdAndTask_Id(UUID id, UUID task_id);
}