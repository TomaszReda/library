package pl.tomekreda.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.ChangePasswordRequest;
import pl.tomekreda.library.validators.PasswordValidators;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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
        System.out.println("++++++++++++++++++++++++++++++++++"+user);
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
        for (User users : userRepository.findAll()) {
            if (users.getEmail().equals(user.getEmail()) && logged.getEmail() != user.getEmail()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik z tym emailem juz istnieje!");
            }
        }




        return ResponseEntity.ok().build();
    }

    public ResponseEntity changePassword(ChangePasswordRequest changePasswordRequest) {
        System.err.println(changePasswordRequest);
        return ResponseEntity.ok().build();
    }
}
