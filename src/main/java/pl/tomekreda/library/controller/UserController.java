package pl.tomekreda.library.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.UserRepository;

import java.util.*;


@CrossOrigin(origins = "http://localhost:4201", maxAge = 3600)
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {


    private final UserRepository userRepository;

    @GetMapping(value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity getUsers() {
        List<User> userList = userRepository.findAll();
        return ResponseEntity.ok(userList);
    }
}
