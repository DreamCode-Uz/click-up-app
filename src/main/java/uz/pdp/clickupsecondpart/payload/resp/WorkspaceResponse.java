package uz.pdp.clickupsecondpart.payload.resp;

import lombok.Data;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.entity.Workspace;

@Data
public class WorkspaceResponse {

    private String name;
    private String color;
    private String initialLetter;
    private User owner;

    public WorkspaceResponse(Workspace workspace) {
        this.name = workspace.getName();
        this.owner = workspace.getOwner();
        this.color = workspace.getColor();
        this.initialLetter = workspace.getInitialLetter();
    }
}
