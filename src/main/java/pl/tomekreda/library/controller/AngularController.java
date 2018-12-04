package pl.tomekreda.library.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.tomekreda.library.repository.UserRepository;

import java.util.UUID;

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

    @GetMapping("/myLibrary/library/add/book")
    public String addBook(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/myLibrary/library/{libraryId}")
    public String searchLibrary(Model model, @PathVariable UUID libraryId) {
        return "forward:/index.html";
    }

    @GetMapping("/myLibrary/library/search/book")
    public String searchBook(Model model) {
        return "forward:/index.html";
    }

}
