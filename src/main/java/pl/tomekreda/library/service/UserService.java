package pl.tomekreda.library.service;

import jdk.nashorn.internal.parser.JSONParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.email.service.EmailService;
import pl.tomekreda.library.model.user.ActivationUserToken;
import pl.tomekreda.library.model.user.ResetPasswordToken;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserState;
import pl.tomekreda.library.repository.ActivationUserTokenRepository;
import pl.tomekreda.library.repository.ResetPasswordTokenRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.ActivationUserRequest;
import pl.tomekreda.library.request.ChangePasswordRequest;
import pl.tomekreda.library.request.ResetPasswordRequest;
import pl.tomekreda.library.validators.PasswordValidators;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.UUID;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))

public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final ResetPasswordTokenRepository resetPasswordTokenRepository;

    private final ActivationUserTokenRepository activationUserTokenRepository;

    public ResponseEntity info() {
        try {
            String loggedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            User user = userRepository.findUserByEmail(loggedUserEmail);
            log.info("[User info]="+user);
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
        log.info("[Change setings before]="+user);

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

        logged.setPassword(passwordEncoder.encode(user.getPassword()));
        if (logged.getResetPasswordToken() != null) {
            logged.getResetPasswordToken().setResetToken(null);
            logged.getResetPasswordToken().setExpireTime(null);
        }
        logged.setEmail(user.getEmail());
        logged.setLastname(user.getLastname());
        logged.setFirstname(user.getFirstname());
        logged.setPhoneNumber(user.getPhoneNumber());
        logged = userRepository.save(logged);

        log.info("[Change setings after]="+user);
        return ResponseEntity.ok(user);
    }

    public ResponseEntity changePassword(ChangePasswordRequest changePasswordRequest) {
        User loged = this.findLoggedUser();
        log.info("[Change password before]="+loged);

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
        if (loged.getResetPasswordToken() != null) {
            loged.getResetPasswordToken().setResetToken(null);
            loged.getResetPasswordToken().setExpireTime(null);
        }
        loged = userRepository.save(loged);
        log.info("[Change password after]="+loged);

        return ResponseEntity.ok(loged);
    }

    public User getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public ResponseEntity getUserInfo(UUID uuid){
        try {
            User user = userRepository.findById(uuid).orElse(null);
            log.info("[User info]="+user);
            return ResponseEntity.ok(user);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity getAllUser(int page, int size) {
        try {
            Pageable pageableRequest = new PageRequest(page, size);
            Page<User> userList = userRepository.findAll(pageableRequest);

            return ResponseEntity.ok(userList);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }


    public ResponseEntity sendEmail(String email) {
        try{
            User user=userRepository.findUserByEmail(email);
            if(user==null){
                return ResponseEntity.badRequest().body("Taki użytkownik nie istnieje");
            }
            ResetPasswordToken resetPasswordToken;
            if (user.getResetPasswordToken() != null) {
                resetPasswordToken = user.getResetPasswordToken();
                resetPasswordToken.setExpireTime(LocalDateTime.now().plusDays(1));
                resetPasswordToken.setResetToken(UUID.randomUUID());
                resetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);

            } else {
                resetPasswordToken = new ResetPasswordToken(UUID.randomUUID(), LocalDateTime.now().plusDays(1));
                resetPasswordToken.setUser(user);
                resetPasswordToken = resetPasswordTokenRepository.save(resetPasswordToken);
            }
            user.setResetPasswordToken(resetPasswordToken);
            userRepository.save(user);
            emailService.sendEmailaResetPassword(email, user.getResetPasswordToken().getResetToken());

            return ResponseEntity.ok().build();
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity resetPassword(ResetPasswordRequest resetPasswordRequest) {
        try {
            if (resetPasswordRequest == null) {
                return ResponseEntity.badRequest().body("Token do resetu hasła wygasł!");
            }

            ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByResetToken(resetPasswordRequest.getResetToken());

            if (resetPasswordToken == null) {
                return ResponseEntity.badRequest().body("Token do resetu hasła wygasł!");
            }
            if (!LocalDateTime.now().isBefore(resetPasswordToken.getExpireTime())) {
                return ResponseEntity.badRequest().body("Token do resetu hasła wygasł!");

            } else {
                String newpassword=generatePassword();
                resetPasswordToken.setExpireTime(null);
                resetPasswordToken.setResetToken(null);
                resetPasswordTokenRepository.save(resetPasswordToken);
                User user = resetPasswordToken.getUser();
                user.setPassword(passwordEncoder.encode(newpassword));
                userRepository.save(user);
                emailService.sendEmailNewPassword(user.getEmail(),newpassword);
                return ResponseEntity.ok(JSONParser.quote("Nowe hasło zostało wysłane na meila"));
            }
        } catch (Exception ex) {

            return ResponseEntity.badRequest().build();
        }
    }

    private String generatePassword(){
        int leftLimit = 47; // letter '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 14;
        Random random = new Random();
        StringBuilder buffer = new StringBuilder(targetStringLength);
        for (int i = 0; i < targetStringLength; i++) {
            int randomLimitedInt = leftLimit + (int)
                    (random.nextFloat() * (rightLimit - leftLimit + 1));
            buffer.append((char) randomLimitedInt);
        }
        String generatedString = buffer.toString();
        return generatedString;
    }


    public ResponseEntity activationUser(ActivationUserRequest activationUserRequest) {
        try {
            if (activationUserRequest == null) {
                return ResponseEntity.badRequest().body("Token do aktywacji konta wygasł!");
            }

            ActivationUserToken activationUserToken = activationUserTokenRepository.findByActiveToken(activationUserRequest.getActivationToken());

            if (activationUserToken == null) {
                return ResponseEntity.badRequest().body("Konto już zostało aktywowane!");
            }
            if (!LocalDateTime.now().isBefore(activationUserToken.getExpireTime())) {
                return ResponseEntity.badRequest().body("Token do aktywacji konta wygasł!");

            } else {
                activationUserToken.setExpireTime(null);
                activationUserToken.setActiveToken(null);
                activationUserTokenRepository.save(activationUserToken);
                User user = activationUserToken.getUser();
                user.setUserState(UserState.ACTIVE);
                userRepository.save(user);
                return ResponseEntity.ok(JSONParser.quote("Konto aktywne! Możesz się zalogować"));
            }
        } catch (Exception ex) {

            return ResponseEntity.badRequest().build();
        }
    }

}
