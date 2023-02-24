package uz.pdp.clickupsecondpart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.pdp.clickupsecondpart.entity.Tag;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByWorkspace_Id(Long workspace_id);

    boolean existsByWorkspace_IdAndName(Long workspace_id, String name);

    boolean existsByWorkspace_IdAndNameAndIdNot(Long workspace_id, String name, Long id);
}