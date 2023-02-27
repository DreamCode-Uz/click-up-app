package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CheckListItemDTO {

    @NotBlank(message = "Name is not be empty")
    private String name;

    private boolean resolved = false;

    @NotNull(message = "Check list Id is not null")
    private UUID checkListId;
}
