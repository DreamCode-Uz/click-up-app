package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class WorkspaceDTO {

    @NotBlank(message = "Workspace name cannot be empty")
    private String name;

    @NotNull(message = "Workspace id cannot be null")
    private String color;

    private UUID avatarId;
}
