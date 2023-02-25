package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class TaskUser extends AbsUUIDEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Task task;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        TaskUser taskUser = (TaskUser) o;
        return getId() != null && Objects.equals(getId(), taskUser.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
