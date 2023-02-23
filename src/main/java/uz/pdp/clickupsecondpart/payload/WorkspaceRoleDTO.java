package uz.pdp.clickupsecondpart.payload;

import lombok.Data;
import uz.pdp.clickupsecondpart.entity.enums.ActionType;
import uz.pdp.clickupsecondpart.entity.enums.WorkspacePermissionName;
import uz.pdp.clickupsecondpart.entity.enums.WorkspaceRoleName;

import java.util.UUID;

@Data
public class WorkspaceRoleDTO {

    private UUID id;

    private String name;

    private WorkspaceRoleName roleName;

    private WorkspacePermissionName permissionName;

    private ActionType actionType;
}
