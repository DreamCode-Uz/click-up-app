package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.pdp.clickupsecondpart.entity.Status;

public interface StatusRepository extends JpaRepository<Status, Long> {
}