package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.enums.TaskPermission;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.util.Objects;

/*******************************
 *   @author Dilshod Fayzullayev
 ********************************/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class ProjectUser extends AbsUUIDEntity {

    @ManyToOne(optional = false)
    private Project project;

    @ManyToOne(optional = false)
    private User user;

    @Enumerated(EnumType.STRING)
    private TaskPermission taskPermission;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ProjectUser that = (ProjectUser) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
