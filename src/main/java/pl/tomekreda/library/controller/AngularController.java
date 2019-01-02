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
    public String home(Model model) { return "forward:/index.html"; }

    @GetMapping("/search/book")
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
    public ResponseEntity cos() { return ResponseEntity.ok().build(); }

    @GetMapping("/myLibrary/library/add/book")
    public String addBook(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/myLibrary/library/{libraryId}")
    public String searchLibrary(Model model, @PathVariable UUID libraryId) {
        return "forward:/index.html";
    }

    @GetMapping("/myLibrary/library/search/book")
    public String searchBookLibraryOwner(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/myLibrary/library/book/{bookId}/details")
    public String bookDetails(Model model, @PathVariable UUID bookId) {
        return "forward:/index.html";
    }

    @GetMapping("/myReserv")
    public String myReserv(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/search/book/{bookId}")
    public String searchDetails(Model model,@PathVariable UUID bookId) {
        return "forward:/index.html";
    }

    @GetMapping("/return/book")
    public String returnBook(Model model,@PathVariable UUID bookId) {
        return "forward:/index.html";
    }

    @GetMapping("/booked/book")
    public String bookedBook(Model model) { return "forward:/index.html"; }

    @GetMapping("/admin/panel")
    public String adminPanel(Model model) { return "forward:/index.html"; }

    @GetMapping("/reserv")
    public String reserv(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/admin/panel/book/list")
    public String adminBookList(Model model) {
        return "forward:/index.html";
    }

    @GetMapping("/admin/panel/user/list")
    public String adminUserList(Model model) { return "forward:/index.html"; }

    @GetMapping("/admin/panel/library/list")
    public String adminLibraryList(Model model) { return "forward:/index.html"; }

    @GetMapping("/user/{userId}")
    public String userDetails(Model model, @PathVariable UUID userId) { return "forward:/index.html"; }

    @GetMapping("/reset/password/{resetPasswordToken}")
    public String resetPasswordToken(Model model, @PathVariable UUID userId) { return "forward:/index.html"; }

    @GetMapping("/user/activation/{tokenId}")
    public String activationUser(Model model, @PathVariable UUID tokenId) { return "forward:/index.html"; }

    @GetMapping("/reset/password/{tokenId}")
    public String resetPassword(Model model, @PathVariable UUID tokenId) { return "forward:/index.html"; }
}
