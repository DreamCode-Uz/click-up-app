package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckListDTO {

    @NotBlank(message = "name must not be empty")
    private String name;

    @NotNull(message = "task ID must not be null")
    private UUID taskId;
}
