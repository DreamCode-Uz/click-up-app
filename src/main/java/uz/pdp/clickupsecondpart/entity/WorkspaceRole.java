package uz.pdp.clickupsecondpart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.pdp.clickupsecondpart.entity.enums.WorkspaceRoleName;
import uz.pdp.clickupsecondpart.entity.template.AbsUUIDEntity;


@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
public class WorkspaceRole extends AbsUUIDEntity {

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Workspace workspace;

    private String name;

    @Enumerated(EnumType.STRING)
    private WorkspaceRoleName extendsRole;
}
