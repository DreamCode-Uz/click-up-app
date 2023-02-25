package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TagDTO {

    @NotNull(message = "Name must not be null")
    private String name;

    @NotNull(message = "Color must not be null")
    private String color;

    @NotNull(message = "Workspace ID must not be null")
    private Long workspaceId;
}
