package pl.tomekreda.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.model.user.ResetPasswordRequest;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.ChangePasswordRequest;
import pl.tomekreda.library.service.UserService;

import java.util.UUID;

@RestController()
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;


    @GetMapping("/online/user/info")
    public ResponseEntity info() {
        return userService.info();
    }


    @PutMapping("/user/change/settings")
    public ResponseEntity changeSettings(@RequestBody User user) {
        return userService.changeSettings(user);
    }

    @PutMapping("/user/change/password")
    public ResponseEntity changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity getUserInfo(@PathVariable UUID id){
        return userService.getUserInfo(id);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/user/get/all")
    public ResponseEntity getAllUser(@RequestParam int page, @RequestParam int size) {
        return userService.getAllUser(page, size);
    }

    @PostMapping("/user/send/reset/password/email")
    public ResponseEntity sendEmail(@RequestBody String email) {
        return userService.sendEmail(email);
    }

    @PutMapping("/user/reset/password")
    public ResponseEntity sendEmail(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        return userService.resetPassword(resetPasswordRequest);
    }

}
