package pl.tomekreda.library.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AngularConctroller {

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
}
