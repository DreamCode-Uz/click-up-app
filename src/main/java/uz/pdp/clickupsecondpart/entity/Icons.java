package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
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
public class Icons extends AbsUUIDEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String initialLetter;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Attachment attachment;

    @PrePersist
    @PreUpdate
    public void setInitialLetter() {
        this.initialLetter = name.substring(0, 1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Icons icons = (Icons) o;
        return getId() != null && Objects.equals(getId(), icons.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
