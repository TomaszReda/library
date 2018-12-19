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
        book.setBookSearch(book.getAuthor() + " " + book.getTitle() + " " + book.getAuthor());
        return book;
    }


    public ResponseEntity deleteBook(UUID bookId, int quant) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (!userService.findLoggedUser().getUserMenager().equals(book.getLibrary().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            if (book.getBookState().equals(BookState.CONFIRMED) || book.getBookState().equals(BookState.BOOKED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ksiązka jest juz przez kogoś zarezerwowana lub jest wyporzyczona");
            }

            if (quant < 1 || quant > book.getQuant()) {
                return ResponseEntity.badRequest().build();
            }

            if (book.getQuant() - quant == 0) {
                book.setBookState(BookState.DELETE);
                bookRepository.save(book);
            } else {
                Book tmp = copyBook(book);
                book.setBookState(BookState.DELETE);
                book.setQuant(quant);
                bookRepository.save(book);
                tmp.setQuant(tmp.getQuant() - quant);
                bookRepository.save(tmp);
                log.info("[Delete book stay]=" + tmp);
            }


            log.info("[Delete book]=" + book);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    Book copyBook(Book book) {
        Book tmp = new Book();
        tmp.setQuant(book.getQuant());
        tmp.setBookState(book.getBookState());
        tmp.setLibrary(book.getLibrary());
        tmp.setISBN(book.getISBN());
        tmp.setPublisher(book.getPublisher());
        tmp.setTitle(book.getTitle());
        tmp.setAuthor(book.getAuthor());
        tmp.setBookCategory(book.getBookCategory());
        tmp.setDate(book.getDate());
        tmp.setBookSearch(book.getBookSearch());
        tmp.setDescription(book.getDescription());
        return tmp;
    }


    public ResponseEntity detailsBook(UUID bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);

            if (!userService.findLoggedUser().getUserMenager().equals(book.getLibrary().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }

            Map<String, Object> bookDetails = createBookMap(book, true);
            log.info("[Book details]=" + bookDetails);
            return ResponseEntity.ok(bookDetails);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity detailsBookCasual(UUID bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            Map<String, Object> bookDetails = createBookMap(book, false);
            log.info("[Book details]=" + bookDetails);
            return ResponseEntity.ok(bookDetails);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Map<String, Object> createBookMap(Book book, boolean isLibraryOwner) throws NoSuchFieldException {
        Utils utils = new Utils();

        Map<String, Object> tmp = new HashMap<>();
        tmp.put(Book.class.getDeclaredField("author").getName(), book.getAuthor());
        tmp.put(Book.class.getDeclaredField("title").getName(), book.getTitle());
        tmp.put(Book.class.getDeclaredField("publisher").getName(), book.getPublisher());
        tmp.put(Book.class.getDeclaredField("date").getName(), book.getDate());
        tmp.put(Book.class.getDeclaredField("ISBN").getName(), book.getISBN());
        tmp.put(Book.class.getDeclaredField("quant").getName(), book.getQuant());
        tmp.put("bookId", book.getID());
        tmp.put(Book.class.getDeclaredField("description").getName(), book.getDescription());
        tmp.put(BookCategory.class.getDeclaredField("categoryType").getName(), book.getBookCategory().getCategoryType());
        tmp.put(Book.class.getDeclaredField("bookState").getName(), utils.convert(book.getBookState()));
        if (isLibraryOwner == false) {
            tmp.put(Library.class.getDeclaredField("city").getName(), book.getLibrary().getCity());
            tmp.put(Library.class.getDeclaredField("latitude").getName(), book.getLibrary().getLatitude());
            tmp.put(Library.class.getDeclaredField("longitude").getName(), book.getLibrary().getLongitude());
            tmp.put(Library.class.getDeclaredField("name").getName(), book.getLibrary().getName());
            tmp.put(Library.class.getDeclaredField("number").getName(), book.getLibrary().getNumber());
            tmp.put(Library.class.getDeclaredField("postalCode").getName(), book.getLibrary().getPostalCode());
            tmp.put(Library.class.getDeclaredField("street").getName(), book.getLibrary().getStreet());
            tmp.put(Library.class.getDeclaredField("email").getName(), book.getLibrary().getEmail());
        }
        return tmp;
    }


    public ResponseEntity reservBook(UUID bookId, int quant) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            if (!book.getBookState().equals(BookState.NOTRESERVED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ksiązka jest juz przez kogoś zarezerwowana lub jest wyporzyczona");
            }
            if (quant < 1 || quant > book.getQuant()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Podajesz nieprawidłową ilość!");
            }
            if (book.getQuant() - quant >= 1) {
                Book tmp = copyBook(book);
                book.setQuant(quant);
                book.setBookState(BookState.BOOKED);
                book.setUserCasual(userService.findLoggedUser().getUserCasual());
                bookRepository.save(book);
                tmp.setQuant(tmp.getQuant() - quant);
                bookRepository.save(tmp);
                log.info("[Reserv book stay]=" + tmp);

            } else {
                book.setBookState(BookState.BOOKED);
                book.setUserCasual(userService.findLoggedUser().getUserCasual());
                bookRepository.save(book);
            }


            log.info("[Delete book]=" + book);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity deleteReservBook(UUID bookId) {
        try {
            Book reservBook = bookRepository.findById(bookId).orElse(null);
            Book notReservBook = bookRepository.findFirstByAuthorAndTitleAndPublisherAndDateAndLibraryAndBookState(reservBook.getAuthor(), reservBook.getTitle(), reservBook.getPublisher(), reservBook.getDate(), reservBook.getLibrary(), BookState.NOTRESERVED);

            if (notReservBook!=null) {

                reservBook.setBookState(BookState.DELETE);
                reservBook.setUserCasual(null);
                bookRepository.save(reservBook);
                notReservBook.setQuant(reservBook.getQuant()+notReservBook.getQuant());
                bookRepository.save(notReservBook);

            } else {
                reservBook.setBookState(BookState.NOTRESERVED);
                reservBook.setUserCasual(null);
                bookRepository.save(reservBook);
            }


            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }




    public ResponseEntity acceptReserv(UUID bookId){
        try{
            Book book=bookRepository.findById(bookId).orElse(null);
            book.setBookState(BookState.CONFIRMED);
            bookRepository.save(book);
            return ResponseEntity.ok().build();
        }
        catch(Exception ex)
        {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity deleteReserv(UUID bookId){
        try{
            Book book=bookRepository.findById(bookId).orElse(null);
            Book notReservBook = bookRepository.findFirstByAuthorAndTitleAndPublisherAndDateAndLibraryAndBookState(book.getAuthor(), book.getTitle(), book.getPublisher(), book.getDate(), book.getLibrary(), BookState.NOTRESERVED);

            if (notReservBook!=null) {
                book.setBookState(BookState.DELETE);
                book.setUserCasual(null);
                bookRepository.save(book);
                notReservBook.setQuant(book.getQuant()+notReservBook.getQuant());
                bookRepository.save(notReservBook);
            } else {
                book.setBookState(BookState.NOTRESERVED);
                book.setUserCasual(null);
                bookRepository.save(book);
            }

            return ResponseEntity.ok().build();
        }
        catch(Exception ex)
        {
            return ResponseEntity.badRequest().build();
        }
    }

}
