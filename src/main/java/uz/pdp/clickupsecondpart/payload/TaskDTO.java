package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class TaskDTO {

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Long statusId;

    @NotNull
    private UUID categoryId;

    @NotNull
    private Long priorityId;

    private UUID parentTask;

    @NotNull
    private Date startedDate;

    @NotNull
    private Date dueDate;

    @NotNull
    private Long estimateTime;

    private String activeDate;
}
