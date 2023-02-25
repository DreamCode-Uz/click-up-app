package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.CheckList;

import java.util.UUID;

@Repository
public interface CheckListRepository extends JpaRepository<CheckList, UUID> {
}