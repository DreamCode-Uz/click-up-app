package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.TaskAttachment;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TaskAttachmentRepository extends JpaRepository<TaskAttachment, UUID> {

    Optional<TaskAttachment> findByTask_IdAndAttachment_Id(UUID task_id, UUID attachment_id);
}