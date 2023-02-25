package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class CommentDTO {

    @NotBlank(message = "Comment text must not be empty!")
    private String text;

    @NotNull(message = "Task ID must not be null!")
    private UUID taskId;
}
