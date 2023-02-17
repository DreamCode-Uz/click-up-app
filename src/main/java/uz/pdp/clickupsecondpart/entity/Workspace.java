package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickupsecondpart.entity.template.AbsLongEntity;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
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
}
