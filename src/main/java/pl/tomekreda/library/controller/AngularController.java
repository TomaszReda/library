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






    public static final String HOME_PAGE = "forward:/index.html";

    @Autowired
    private UserRepository userRepository;

    private String page() {
        return HOME_PAGE;
    }

    @GetMapping("/")
    public String page(Model model) {
        return page();
    }

    @GetMapping("/home")
    public String home(Model model) {
        return page();
    }

    @GetMapping("/search/book")
    public String users(Model model) {
        return page();
    }

    @GetMapping("/accountSettings")
    public String accountSettings(Model model) {
        return page();
    }

    @GetMapping("/addLibrary")
    public String addLibrary(Model model) {
        return page();
    }

    @GetMapping("/myLibrary")
    public String myLibrary(Model model) {
        return page();
    }

    @ResponseBody
    @GetMapping("/api/tokenValid")
    public ResponseEntity cos() {
        return ResponseEntity.ok().build();
    }

    @GetMapping("/myLibrary/library/add/book")
    public String addBook(Model model) {
        return page();
    }

    @GetMapping("/myLibrary/library/{libraryId}")
    public String searchLibrary(Model model, @PathVariable UUID libraryId) {
        return page();
    }

    @GetMapping("/myLibrary/library/search/book")
    public String searchBookLibraryOwner(Model model) {
        return page();
    }

    @GetMapping("/myLibrary/library/book/{bookId}/details")
    public String bookDetails(Model model, @PathVariable UUID bookId) {
        return page();
    }

    @GetMapping("/myReserv")
    public String myReserv(Model model) {
        return page();
    }

    @GetMapping("/search/book/{bookId}")
    public String searchDetails(Model model, @PathVariable UUID bookId) {
        return page();
    }

    @GetMapping("/return/book")
    public String returnBook(Model model, @PathVariable UUID bookId) {
        return page();
    }

    @GetMapping("/booked/book")
    public String bookedBook(Model model) {
        return page();
    }

    @GetMapping("/admin/panel")
    public String adminPanel(Model model) {
        return page();
    }

    @GetMapping("/reserv")
    public String reserv(Model model) {
        return page();
    }

    @GetMapping("/admin/panel/book/list")
    public String adminBookList(Model model) {
        return page();
    }

    @GetMapping("/admin/panel/user/list")
    public String adminUserList(Model model) {
        return page();
    }

    @GetMapping("/admin/panel/library/list")
    public String adminLibraryList(Model model) {
        return page();
    }

    @GetMapping("/user/{userId}")
    public String userDetails(Model model, @PathVariable UUID userId) {
        return page();
    }

    @GetMapping("/reset/password/{resetPasswordToken}")
    public String resetPasswordToken(Model model, @PathVariable UUID resetPasswordToken) {
        return page();
    }

    @GetMapping("/user/activation/{activationToken}")
    public String activationUser(Model model, @PathVariable UUID activationToken) {
        return page();
    }

}
