package pl.tomekreda.library.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tomekreda.library.request.AddUserCasualRequest;

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

    @GetMapping("/search")
    public String users(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/accountSettings")
    public String accountSettings(Model model) {
        return "forward:/index.html";
    }

    @GetMapping( "/addLibrary")
    public String addLibrary(Model model) {
        return "forward:/index.html";
    }


    @ResponseBody
    @GetMapping("/api/tokenValid")
    public ResponseEntity cos() {

        return ResponseEntity.ok().build();
    }

}
