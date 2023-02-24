package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.enums.StatusName;
import uz.pdp.clickupsecondpart.entity.template.AbsLongEntity;

import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Status extends AbsLongEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatusName status;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Category category;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Status status = (Status) o;
        return getId() != null && Objects.equals(getId(), status.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
