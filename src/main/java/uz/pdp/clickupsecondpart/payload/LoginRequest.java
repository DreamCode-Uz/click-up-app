package uz.pdp.clickupsecondpart.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginRequest {

    @NotNull(message = "Email cannot be null")
    @Email(message = "Email is not valid")
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 7, message = "Password must be at least 7 characters long")
    private String password;
}
