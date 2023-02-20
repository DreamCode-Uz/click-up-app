package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.enums.AccessType;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Space extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(name = "access_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AccessType accessType;

    @Column(name = "initial_letter", nullable = false)
    private String initialLetter;

    @OneToOne
    private Attachment avatar;

    @ManyToOne(optional = false)
    private Workspace workspace;

    @ManyToOne(optional = false)
    private User owner;

    @PrePersist
    @PreUpdate
    public void setInitialLetter() {
        initialLetter = name.substring(0, 1);
    }

    public Space(String name, String color, AccessType accessType, Workspace workspace, User owner) {
        this.name = name;
        this.color = color;
        this.accessType = accessType;
        this.workspace = workspace;
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Space space = (Space) o;
        return getId() != null && Objects.equals(getId(), space.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
