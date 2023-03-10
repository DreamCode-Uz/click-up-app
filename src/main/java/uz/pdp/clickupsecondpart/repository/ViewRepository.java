package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.View;

import java.util.UUID;

@Repository
public interface ViewRepository extends JpaRepository<View, UUID> {
}