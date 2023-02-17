package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.Attachment;

import java.util.List;
import java.util.UUID;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

    List<Attachment> findAllByCreatedBy_Id(UUID createdBy_id);
}
