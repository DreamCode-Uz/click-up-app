package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.clickupsecondpart.entity.Workspace;

import java.util.UUID;

@Data
public class WorkspaceDTO {

    private Long id;

    @NotBlank(message = "Workspace name cannot be empty")
    private String name;

    @NotNull(message = "Workspace id cannot be null")
    private String color;

    private UUID avatarId;

    private UUID ownerId;

    private String initialLetter;

    public WorkspaceDTO(Workspace workspace) {
        this.id = workspace.getId();
        this.name = workspace.getName();
        this.color = workspace.getColor();
        this.avatarId = workspace.getAvatarId();
        this.ownerId = workspace.getOwner().getId();
        this.initialLetter = workspace.getInitialLetter();
    }
}
