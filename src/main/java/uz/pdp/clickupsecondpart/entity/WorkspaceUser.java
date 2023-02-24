package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.sql.Timestamp;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class WorkspaceUser extends AbsUUIDEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private Workspace workspace;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @ToString.Exclude
    private WorkspaceRole workspaceRole;

    @Column(nullable = false)
    private Timestamp dataJoined;

    private Timestamp dateInvited;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        WorkspaceUser that = (WorkspaceUser) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
