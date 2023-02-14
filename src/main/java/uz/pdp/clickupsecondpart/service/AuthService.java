package uz.pdp.clickupsecondpart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.component.MailSender;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.entity.enums.SystemRoleName;
import uz.pdp.clickupsecondpart.payload.RegisterRequest;
import uz.pdp.clickupsecondpart.repository.UserRepository;

import java.util.Random;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;
import static org.springframework.http.ResponseEntity.status;

@Service
public class AuthService implements UserDetailsService {
    private final String BASE_VERIFY_URL = "http://localhost:8080/api/auth/verify";
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final MailSender mailSender;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, MailSender mailSender) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

    public ResponseEntity<?> register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail()))
            return status(UNPROCESSABLE_ENTITY).body("Email already exists");
        User user = new User(
                request.getFullName(),
                request.getEmail(),
                passwordEncoder.encode(request.getPassword()),
                SystemRoleName.SYSTEM_USER
        );
        int code = new Random().nextInt(6);
        System.out.println(code);
        user.setActivationCode(String.valueOf(code));
        user = userRepository.save(user);
        mailSender.sendMail(
                "Account activation for the ClickUp project",
                String.format("%s/%s", BASE_VERIFY_URL, code),
                user.getEmail()
        );
        return status(CREATED).body("User registered successfully");
    }

    public ResponseEntity<?> verifyEmailCode(String code) {
        return null;
    }
}
