package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddBookRequest;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final LibraryRepository libraryRepository;

    public ResponseEntity addBook(AddBookRequest addBookRequest) {
        try {
            Library library = libraryRepository.findById(addBookRequest.getLibraryId()).orElse(null);
            Book tmp = createBook(addBookRequest, library);
            bookRepository.save(tmp);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    private Book createBook(AddBookRequest addBookRequest, Library library) {
        Book book = new Book();
        book.setLibrary(library);
        book.setAuthor(addBookRequest.getAuthor());
        book.setTitle(addBookRequest.getTitle());
        book.setPublisher(addBookRequest.getPublisher());
        book.setBookState(BookState.NOTRESERVED);
        book.setDate(addBookRequest.getDate());
        book.setDescription(addBookRequest.getDescription());
        book.setISBN(addBookRequest.getIsbn());
        book.setQuant(addBookRequest.getQuant());

        return book;
    }

}
