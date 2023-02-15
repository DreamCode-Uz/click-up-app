package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "Full name is required")
    private String fullName;

    @NotBlank(message = "Email is required")
    private String email;

    @NotNull(message = "Password is required")
    @Size(min = 7, message = "Password must be at least 7 characters")
    private String password;
}
