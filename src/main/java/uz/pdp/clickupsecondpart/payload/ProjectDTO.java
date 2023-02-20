package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class ProjectDTO {

    @NotBlank(message = "Project name cannot be empty")
    private String name;

    @NotNull(message = "Space ID cannot be empty")
    private UUID spaceId;

    @NotBlank(message = "Project Access type cannot be empty (PUBLIC OR PRIVATE)")
    private String accessType;

    private boolean archived;

    @NotBlank(message = "Color cannot be empty")
    private String color;
}
