package pl.tomekreda.library.controller;


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

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserRepository userRepository;


    @GetMapping(value = "/users",produces = MediaType.APPLICATION_JSON_VALUE)
    public   List<User> getUsers() {
        List<User> userList = userRepository.findAll();
        System.err.print(userList);
        return userList;
    }
}
