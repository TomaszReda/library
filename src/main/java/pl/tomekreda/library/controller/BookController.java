package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.request.AddBookRequest;
import pl.tomekreda.library.service.BookService;

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
}
