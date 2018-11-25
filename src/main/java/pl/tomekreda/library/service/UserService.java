package pl.tomekreda.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.ChangePasswordRequest;
import pl.tomekreda.library.validators.PasswordValidators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.beans.Encoder;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    public ResponseEntity info() {
        try {
            String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findUserByEmail(loggedUserEmail);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public User findLoggedUser() {
        String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findUserByEmail(loggedUserEmail);
        return user;
    }

    public ResponseEntity changeSettings(User user) {
        User logged = this.findLoggedUser();
        if (user.getPhoneNumber() == 0 || user.getEmail() == null || user.getEmail() == ""
                || user.getFirstname() == null || user.getFirstname() == ""
                || user.getPassword() == null || user.getPassword() == ""
                || user.getLastname() == null || user.getLastname() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uzupełnij wszystkie pola!");
        }
        if (!user.getEmail().contains("@")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Podaj poprawny email!");
        }
        if (logged.getEmail().equals(user.getEmail())) {
        } else {
            for (User users : userRepository.findAll()) {
                if (users.getEmail().equals(user.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik z tym emailem juz istnieje!");
                }
            }
        }
        if (!passwordEncoder.matches(user.getPassword(), logged.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Błędne hasło!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setUserRoles(logged.getUserRoles());
        user = userRepository.save(user);

        return ResponseEntity.ok(user);
    }

    public ResponseEntity changePassword(ChangePasswordRequest changePasswordRequest) {
        User loged = this.findLoggedUser();
        if (changePasswordRequest.getNewpassword() == null || changePasswordRequest.getNewpassword() == "" ||
                changePasswordRequest.getNewpasswordrepeat() == null || changePasswordRequest.getNewpasswordrepeat() == "" ||
                changePasswordRequest.getOldpassword() == null || changePasswordRequest.getOldpassword() == "") {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uzupełnij wszystkie pola!");
        }
        if (!changePasswordRequest.getNewpassword().equals(changePasswordRequest.getNewpasswordrepeat())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hasła nie są takie same!");
        }
        if (changePasswordRequest.getNewpassword().length() <= 8 && changePasswordRequest.getNewpassword().length() >= 17) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hasło musi mieć minimum 8 a maksimum 16 znaków!");
        }
        if (!passwordEncoder.matches(changePasswordRequest.getOldpassword(), loged.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Stare hasło jest błędne!");

        }
        if (!PasswordValidators.valid(changePasswordRequest.getNewpassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hasło musi mieć 1 dużą i mała literę oraz cyfrę!");
        }
        loged.setPassword(passwordEncoder.encode(changePasswordRequest.getNewpassword()));
        loged = userRepository.save(loged);

        return ResponseEntity.ok(loged);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
