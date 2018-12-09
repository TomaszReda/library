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
    public ResponseEntity deleteBook(@RequestBody int quant,@PathVariable UUID bookId) {
        return bookService.deleteBook(bookId,quant);
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
    public ResponseEntity reservBook(@RequestBody int quant,@PathVariable UUID bookId) {
        return bookService.reservBook(bookId,quant);
    }

}
