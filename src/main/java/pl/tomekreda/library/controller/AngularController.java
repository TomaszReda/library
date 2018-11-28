package pl.tomekreda.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddUserCasualRequest;
import pl.tomekreda.library.service.UserService;

import javax.transaction.Transactional;

@Controller
public class AngularController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String page(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/home")
    public String home(Model model) {
        System.err.println(userRepository.findAll());

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

    @GetMapping("/addLibrary")
    public String addLibrary(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/myLibrary")
    public String myLibrary(Model model) {
        return "forward:/index.html";
    }

    @ResponseBody
    @GetMapping("/api/tokenValid")
    public ResponseEntity cos() {

        return ResponseEntity.ok().build();
    }

}
