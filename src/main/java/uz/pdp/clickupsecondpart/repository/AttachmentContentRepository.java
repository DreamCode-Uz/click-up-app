package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.AttachmentContent;

@Repository
public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, Long> {
}
