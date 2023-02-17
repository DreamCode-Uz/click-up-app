package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.clickupsecondpart.entity.enums.ActionType;

import java.util.UUID;

@Data
public class MemberDTO {

    @NotNull(message = "Member ID cannot be null")
    private UUID userId;

    @NotNull(message = "Role ID cannot be null")
    private UUID roleId;

    @NotNull(message = "Action cannot be null")
    private ActionType actionType;
}
