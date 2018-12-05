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
    public ResponseEntity deleteBook(@RequestBody String quant,@PathVariable UUID bookId) {
        return bookService.deleteBook(bookId,quant);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/book/{bookId}/details")
    public ResponseEntity detailsBook(@PathVariable UUID bookId) {
        return bookService.detailsBook(bookId);
    }


}
