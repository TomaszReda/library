package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookCategory;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.repository.BookCategoryRepository;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddBookRequest;
import pl.tomekreda.library.utils.Utils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class BookService {

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final LibraryRepository libraryRepository;

    private final BookCategoryRepository bookCategoryRepository;

    private final UserService userService;

    public ResponseEntity addBook(AddBookRequest addBookRequest) {
        try {
            log.info("[Add book request]=" + addBookRequest);
            Library library = libraryRepository.findById(addBookRequest.getLibraryId()).orElse(null);
            Book tmp = createBook(addBookRequest, library);
            bookRepository.save(tmp);

            log.info("[Added book]=" + tmp);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    private Book createBook(AddBookRequest addBookRequest, Library library) {
        BookCategory bookCategory = bookCategoryRepository.findFirstByCategoryType(addBookRequest.getBookCategory());
        Book book = new Book();
        book.setBookCategory(bookCategory);
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


    public ResponseEntity deleteBook(UUID bookId,String quant) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (!userService.findLoggedUser().getUserMenager().equals(book.getLibrary().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            if (book.getBookState().equals(BookState.CONFIRMED) || book.getBookState().equals(BookState.BOOKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ksiązka jest juz przez kogoś zarezerwowana lub jest wyporzyczona");
            }
            book.setBookState(BookState.DELETE);
            book = bookRepository.save(book);

            log.info("[Delete book]=" + book);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity detailsBook(UUID bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);

            if (!userService.findLoggedUser().getUserMenager().equals(book.getLibrary().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }

            Map<String, Object> bookDetails = createMap(book);
            log.info("[Book details]=" + bookDetails);
            return ResponseEntity.ok(bookDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Map<String, Object> createMap(Book book) throws NoSuchFieldException {
        Utils utils = new Utils();

        Map<String, Object> tmp = new HashMap<>();
        tmp.put(Book.class.getDeclaredField("author").getName(), book.getAuthor());
        tmp.put(Book.class.getDeclaredField("title").getName(), book.getTitle());
        tmp.put(Book.class.getDeclaredField("publisher").getName(), book.getPublisher());
        tmp.put(Book.class.getDeclaredField("date").getName(), book.getDate());
        tmp.put(Book.class.getDeclaredField("ISBN").getName(), book.getISBN());
        tmp.put(Book.class.getDeclaredField("quant").getName(), book.getQuant());
        tmp.put(Book.class.getDeclaredField("description").getName(),book.getDescription());
        tmp.put(BookCategory.class.getDeclaredField("categoryType").getName(), book.getBookCategory().getCategoryType());
        tmp.put(Book.class.getDeclaredField("bookState").getName(), utils.convert(book.getBookState()));

        return tmp;
    }


}
