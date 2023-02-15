package uz.pdp.clickupsecondpart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.clickupsecondpart.component.SendMail;
import uz.pdp.clickupsecondpart.entity.User;
import uz.pdp.clickupsecondpart.entity.enums.SystemRoleName;
import uz.pdp.clickupsecondpart.payload.RegisterRequest;
import uz.pdp.clickupsecondpart.repository.UserRepository;

import java.util.Optional;
import java.util.Random;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.ResponseEntity.*;

@Service
public class AuthService implements UserDetailsService {
    private final String BASE_VERIFY_URL = "http://localhost:8080/api/auth/verify";
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    private SendMail sendMail;

    @Autowired
    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
//        this.mailSender = mailSender;
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
        int code = new Random().nextInt(9999);
        System.out.println(code);
        user.setActivationCode(String.valueOf(code));
        user = userRepository.save(user);
        sendMail.sendMail(
                "Account activation for the ClickUp project",
                String.format("%s/c=%s/e=%s", BASE_VERIFY_URL, user.getActivationCode(), user.getEmail()),
                user.getEmail()
        );
        return status(CREATED).body("User registered successfully");
    }

    public ResponseEntity<?> verifyEmailCode(String code, String email) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isEmpty()) return status(NOT_FOUND).body("User not found");
        User user = optionalUser.get();
//        Ushbu holatda hisob oldin faollashtirilgan lekin keyinchalik hisob admin tomonida blocklanga bo'lishi mumkin!
        if (user.getActivationCode() == null && !user.isEnabled())
            return status(NOT_ACCEPTABLE).body("The user account cannot be activated because you do not have an active activation code. Please contact the developer if you want to know the reason.");
        if (!user.getActivationCode().equals(code)) return badRequest().body("Invalid code");
        user.setActivationCode(null);
        user.setEnabled(true);
        userRepository.save(user);
        return ok("User verified successfully");
    }
}
