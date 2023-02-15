package uz.pdp.clickupsecondpart.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.pdp.clickupsecondpart.payload.RegisterRequest;
import uz.pdp.clickupsecondpart.service.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    public ResponseEntity<?> login() {
        return null;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest registerRequest) {
        return authService.register(registerRequest);
    }

    @GetMapping("/verify/{code}")
    public ResponseEntity<?> verifyEmailCode(@PathVariable(name = "code") String code) {
        return authService.verifyEmailCode(code);
    }
}
