package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class PriorityDTO {

    @NotBlank(message = "Name must not be empty")
    private String name;

    @NotBlank(message = "Color must not be empty")
    private String color;

    @NotNull(message = "Icon id must not be null")
    private UUID iconId;
}
