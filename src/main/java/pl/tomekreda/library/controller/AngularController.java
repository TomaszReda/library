package pl.tomekreda.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

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

    @GetMapping("/users")
    public String users(Model model) {
        return "forward:/index.html";
    }
}
