package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.request.AddBookRequest;
import pl.tomekreda.library.service.BookService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookController {

    private final BookService bookService;

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @PostMapping("/book/add")
    public ResponseEntity addBook(@RequestBody AddBookRequest addBookRequest) {
        return bookService.addBook(addBookRequest);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @PostMapping("/book/{bookId}/delete/")
    public ResponseEntity deleteBook(@RequestBody int quant, @PathVariable UUID bookId) {
        return bookService.deleteBook(bookId, quant);
    }

    //Details for library owner
    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/book/{bookId}/details")
    public ResponseEntity detailsBook(@PathVariable UUID bookId) {
        return bookService.detailsBook(bookId);
    }

    //Details for casual and unregister user
    @GetMapping("/book/details/{bookId}")
    public ResponseEntity detailsBook2(@PathVariable UUID bookId) {
        return bookService.detailsBookCasual(bookId);
    }

    @PreAuthorize("hasAuthority('ROLE_CASUAL_USER')")
    @PostMapping("/book/{bookId}/reserv")
    public ResponseEntity reservBook(@RequestBody int quant, @PathVariable UUID bookId) {
        return bookService.reservBook(bookId, quant);
    }

    @PreAuthorize("hasAuthority('ROLE_CASUAL_USER')")
    @PostMapping("/book/{bookId}/delete/reserv")
    public ResponseEntity deleteReservBook(@PathVariable UUID bookId) {
        return bookService.deleteReservBook(bookId);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/book/{bookId}/reserv/accept")
    public ResponseEntity acceptReserv(@PathVariable UUID bookId) {
        return bookService.acceptReserv(bookId);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/book/{bookId}/reserv/delete")
    public ResponseEntity deleteReserv(@PathVariable UUID bookId) {
        return bookService.deleteReserv(bookId);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/book/{bookId}/book/return")
    public ResponseEntity returnBook(@PathVariable UUID bookId) {
        return bookService.returnBook(bookId);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/book/get/all")
    public ResponseEntity getAllBook(@RequestParam int page, @RequestParam int size) {
        return bookService.getAllBooks(page, size);
    }
}
