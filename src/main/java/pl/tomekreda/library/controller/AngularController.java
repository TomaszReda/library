package pl.tomekreda.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AngularController {

    @GetMapping("/")
    public String page(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/home")
    public String home(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/login")
    public String login(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/search")
    public String users(Model model) {
        return "forward:/index.html";
    }


    @ResponseBody
    @GetMapping("/api/tokenValid")
    public ResponseEntity cos() {

        return ResponseEntity.ok().build();
    }

}
