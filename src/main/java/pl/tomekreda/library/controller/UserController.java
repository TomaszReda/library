package pl.tomekreda.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.request.ChangePasswordRequest;
import pl.tomekreda.library.service.UserService;

@RestController()
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

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

}
