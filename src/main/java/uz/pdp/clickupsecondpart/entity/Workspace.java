package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.template.AbsLongEntity;

import java.util.Objects;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"workspace_name", "owner_id"}))
public class Workspace extends AbsLongEntity {

    @Column(name = "workspace_name", nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @ManyToOne(optional = false)
    private User owner;

    @Column(name = "initial_letter", nullable = false)
    private String initialLetter;

    private UUID avatarId;

    @PrePersist
    @PreUpdate
    public void prePersistUpdate() {
        this.initialLetter = this.name.substring(0, 1);
    }

    public Workspace(String name, String color, User owner, UUID avatarId) {
        this.name = name;
        this.color = color;
        this.owner = owner;
        this.avatarId = avatarId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Workspace workspace = (Workspace) o;
        return getId() != null && Objects.equals(getId(), workspace.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
