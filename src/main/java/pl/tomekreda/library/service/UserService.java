package pl.tomekreda.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;

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
}
