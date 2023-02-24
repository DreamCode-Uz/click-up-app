package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.enums.WorkspaceRoleName;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "workspace_role", uniqueConstraints = {@UniqueConstraint(columnNames = {"workspace_id", "name"})})
public class WorkspaceRole extends AbsUUIDEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Workspace workspace;

    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendsRole;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkspaceRole that = (WorkspaceRole) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
