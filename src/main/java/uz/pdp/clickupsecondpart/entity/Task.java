package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.Hibernate;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Task extends AbsUUIDEntity {

    private String name;

    private String description;

    private Timestamp startedDate;

    private Timestamp dueTimeHas;

    private Long estimateTime;

    private LocalDate activeDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Exclude
    private Task parentTask;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Task task = (Task) o;
        return getId() != null && Objects.equals(getId(), task.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
