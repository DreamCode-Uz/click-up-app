package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import uz.pdp.clickupsecondpart.entity.enums.StatusName;

import java.util.UUID;

@Data
public class StatusDTO {

    @NotNull(message = "Name must not be null")
    private String name;

    @NotNull(message = "Color must not be null")
    private String color;

    @NotNull(message = "Status name must not be null")
    private StatusName status;

    @NotNull(message = "Project ID must not be null")
    private UUID projectId;

    @NotNull(message = "Category ID must not be null")
    private UUID categoryId;
}
