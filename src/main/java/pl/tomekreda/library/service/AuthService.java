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
import org.springframework.web.bind.annotation.RequestBody;
import pl.tomekreda.library.model.user.*;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddUserCasualRequest;
import pl.tomekreda.library.request.AddUserLibraryOwnerRequest;
import pl.tomekreda.library.validators.PasswordValidators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthenticationResponse login(@RequestBody Credentials credentials, HttpServletRequest request) {
        final UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword());

        final Authentication authentication = this.authenticationManager.authenticate(token);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);

        final HttpSession httpSession = request.getSession(true);

        httpSession.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext()
        );

        return new AuthenticationResponse(authentication.getName(), httpSession.getId());

    }


    public ResponseEntity registerCasualUser(AddUserCasualRequest user) {
        try {

            if (user.getPhoneNumber() == 0 || user.getEmail() == null || user.getEmail() == ""
                    || user.getFirstname() == null || user.getFirstname() == ""
                    || user.getPassword() == null || user.getPassword() == ""
                    || user.getLastname() == null || user.getLastname() == "") {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uzupełnij wszystkie pola!");
            }
            if (!user.getEmail().contains("@")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Podaj poprawny email!");
            }
            if (user.getPassword().length() <= 8 && user.getPassword().length() >= 17) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hasło musi mieć minimum 8 a maksimum 16 znaków!");
            }
            if (!PasswordValidators.valid(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hasło musi mieć 1 dużą i mała literę oraz cyfrę!");
            }
            for (User users : userRepository.findAll()) {
                if (users.getEmail().equals(user.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik z tym emailem juz istnieje!");
                }
            }

            User tmp = new User();
            tmp.setEmail(user.getEmail());
            tmp.setFirstname(user.getFirstname());
            tmp.setLastname(user.getLastname());
            tmp.setPhoneNumber(user.getPhoneNumber());
            tmp.setPassword(passwordEncoder.encode(user.getPassword()));
            tmp.setUserCasual(null);
            tmp.setUserMenager(null);
            UserRoles useroles=new UserRoles();
            useroles.setUserRole(UserRoleEnum.CASUAL_USER);
            tmp.getUserRoles().add(useroles);
            userRepository.save(tmp);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

    public ResponseEntity registerOwnerUser(AddUserLibraryOwnerRequest user) {
        try {
            if (user.getPhoneNumber() == 0 || user.getEmail() == null || user.getEmail() == ""
                    || user.getFirstname() == null || user.getFirstname() == ""
                    || user.getPassword() == null || user.getPassword() == ""
                    || user.getLastname() == null || user.getLastname() == "") {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Uzupełnij wszystkie pola!");
            }

            if (!user.getEmail().contains("@")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Podaj poprawny email!");
            }
            if (user.getPassword().length() < 8 || user.getPassword().length() > 16) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hasło musi mieć minimum 8 a maksimum 16 znaków!");
            }
            if (!PasswordValidators.valid(user.getPassword())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Hasło musi mieć 1 dużą i mała literę oraz cyfrę!");
            }
            for (User users : userRepository.findAll()) {
                if (users.getEmail().equals(user.getEmail())) {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Użytkownik z tym emailem juz istnieje!");
                }
            }

            User tmp = new User();
            tmp.setPhoneNumber(user.getPhoneNumber());
            tmp.setEmail(user.getEmail());
            tmp.setFirstname(user.getFirstname());
            tmp.setLastname(user.getLastname());
            tmp.setPassword(passwordEncoder.encode(user.getPassword()));
            tmp.setUserCasual(null);
            tmp.setUserMenager(null);
            UserRoles useroles=new UserRoles();
            useroles.setUserRole(UserRoleEnum.LIBRARY_OWNER);
            tmp.getUserRoles().add(useroles);
            userRepository.save(tmp);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }

}
