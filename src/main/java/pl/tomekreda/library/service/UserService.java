package pl.tomekreda.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.ChangePasswordRequest;

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


    public ResponseEntity changeSettings(User user) {
        System.err.print(user);
        return ResponseEntity.ok().build();
    }

    public ResponseEntity changePassword(ChangePasswordRequest changePasswordRequest) {
        System.err.print(changePasswordRequest);
        return ResponseEntity.ok().build();
    }
}
