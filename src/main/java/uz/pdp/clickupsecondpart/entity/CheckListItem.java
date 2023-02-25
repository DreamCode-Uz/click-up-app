package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.util.Objects;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CheckListItem extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private CheckList checkList;

    @Column(nullable = false)
    private boolean resolved;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User assignedUser;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        CheckListItem that = (CheckListItem) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
