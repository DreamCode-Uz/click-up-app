package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.CheckListItem;

import java.util.List;
import java.util.UUID;

@Repository
public interface CheckListItemRepository extends JpaRepository<CheckListItem, UUID> {

    List<CheckListItem> findAllByCheckList_Id(UUID checkList_id);

    boolean existsByCheckList_IdAndId(UUID checkList_id, UUID id);

    CheckListItem getByCheckListId(UUID checkList_id);
}