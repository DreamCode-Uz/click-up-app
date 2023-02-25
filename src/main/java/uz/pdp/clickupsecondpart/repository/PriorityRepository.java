package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.Priority;

@Repository
public interface PriorityRepository extends JpaRepository<Priority, Long> {

    boolean existsByName(String name);
}