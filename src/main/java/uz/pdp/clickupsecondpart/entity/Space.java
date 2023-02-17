package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickupsecondpart.entity.enums.AccessType;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
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

    @PrePersist
    @PreUpdate
    public void setInitialLetter() {
        initialLetter = name.substring(0, 1);
    }
}
