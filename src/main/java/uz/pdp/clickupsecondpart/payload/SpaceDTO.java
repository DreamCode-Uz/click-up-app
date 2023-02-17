package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.clickupsecondpart.entity.enums.AccessType;

import java.util.UUID;

@Data
public class SpaceDTO {

    @NotBlank(message = "Space name cannot be empty")
    private String name;

    @NotBlank(message = "Space color cannot be empty")
    private String color;

    @NotNull(message = "Access type cannot be empty. (PUBLIC or PRIVATE)")
    private AccessType accessType;

    private UUID avatarId;
}
