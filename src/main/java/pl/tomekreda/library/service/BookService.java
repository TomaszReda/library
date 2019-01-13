package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookCategory;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.message.Message;
import pl.tomekreda.library.model.message.MessageToCasualUser;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.task.*;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.quartz.service.ReceiveTheBookForUserService;
import pl.tomekreda.library.quartz.service.ReminderOfGivingABookForLibraryService;
import pl.tomekreda.library.quartz.service.ReminderOfGivingABookForUserService;
import pl.tomekreda.library.repository.*;
import pl.tomekreda.library.request.AddBookRequest;
import pl.tomekreda.library.utils.DataUtils;
import pl.tomekreda.library.utils.MessageUtils;
import pl.tomekreda.library.utils.Utils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
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

    private final TaskForUserRepository taskForUserRepository;

    private final TaskForLibraryRepository taskForLibraryRepository;

    private final ReceiveTheBookForUserService receiveTheBookForUserService;

    private final SchedulerFactoryBean schedulerFactoryBean;

    private final ReminderOfGivingABookForLibraryService reminderOfGivingABookForLibraryService;

    private final ReminderOfGivingABookForUserService reminderOfGivingABookForUserService;

    private final MessageToCasualUserRepository messageToCasualUserRepository;

    private final MessageToLibraryOwnerRepository messageToLibraryOwnerRepository;

    @Value("${ksiazeczka.quartz.deploy}")
    private int deploy;

    public ResponseEntity addBook(AddBookRequest addBookRequest) {
        try {
            log.info("[Add book request]=" + addBookRequest);
            User user = userService.findLoggedUser();
            Library library = libraryRepository.findById(addBookRequest.getLibraryId()).orElse(null);
            Book tmp = createBook(addBookRequest, library);
            tmp.setUserMenager(user.getUserMenager());
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
        tmp.setUserMenager(book.getUserMenager());
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
            log.info("Create quartz task"+ LocalTime.now().toString());
            Book book = bookRepository.findById(bookId).orElse(null);
            if (!book.getBookState().equals(BookState.NOTRESERVED)) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Ksiązka jest juz przez kogoś zarezerwowana lub jest wyporzyczona");
            }
            if (quant < 1 || quant > book.getQuant()) {
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Podajesz nieprawidłową ilość!");
            }
            TaskForUser taskForUser;
            MessageToCasualUser messageToCasualUser;
            MessageToLibraryOwner messageToLibraryOwner;
            String contentForLibraryOwner="";
            String contentForCasualUser="";
            if (book.getQuant() - quant >= 1) {
                Book tmp = copyBook(book);
                book.setQuant(quant);
                book.setUserCasual(userService.findLoggedUser().getUserCasual());
                book.setBookState(BookState.BOOKED);
                book = bookRepository.save(book);
                taskForUser = new TaskForUser(userService.findLoggedUser(), LocalDateTime.now(), LocalDateTime.now().plusDays(3), TaskStatus.TO_DO, book, book.getLibrary(), TaskForUserType.GET_THE_BOOK);
                taskForUser = taskForUserRepository.save(taskForUser);
                contentForCasualUser="Zarezerwowałeś książke " +book.getTitle()+" "+book.getAuthor()+". Masz 3 dni na jego odebranie( Termin mija )"+taskForUser.getDateExpiration();
                contentForLibraryOwner=" Uzytkownik"+ userService.findLoggedUser().getEmail()+" zarezerwował twoja książke "+book.getTitle()+" "+book.getAuthor();
                messageToLibraryOwner = new MessageToLibraryOwner(contentForLibraryOwner, MessageUtils.MESSAGE_RESERV_BOOK_TO_LIBRARY_OWNER_TITLE, book.getLibrary());
                messageToCasualUser = new MessageToCasualUser(contentForCasualUser, MessageUtils.MESSAGE_RESERV_BOOK_TO_CASUAL_USER_TITLE, userService.findLoggedUser(), taskForUser);
                messageToLibraryOwner = messageToLibraryOwnerRepository.save(messageToLibraryOwner);
                messageToCasualUserRepository.save(messageToCasualUser);
                messageToLibraryOwnerRepository.save(messageToLibraryOwner);
                tmp.setQuant(tmp.getQuant() - quant);
                book = bookRepository.save(tmp);
                log.info("[Reserv book]=" + tmp);

            } else {

                book.setBookState(BookState.BOOKED);
                book.setUserCasual(userService.findLoggedUser().getUserCasual());
                book = bookRepository.save(book);
                taskForUser = new TaskForUser(userService.findLoggedUser(), LocalDateTime.now(), LocalDateTime.now().plusDays(3), TaskStatus.TO_DO, book, book.getLibrary(), TaskForUserType.GET_THE_BOOK);
                taskForUserRepository.save(taskForUser);
                contentForLibraryOwner=" Uzytkownik"+ userService.findLoggedUser().getEmail()+" zarezerwował twoja książke "+book.getTitle()+" "+book.getAuthor();
                contentForCasualUser=" Zarezerwowałeś książke " +book.getTitle()+" "+book.getAuthor()+". Masz 3 dni na jego odebranie( Termin mija )"+taskForUser.getDateExpiration();
                messageToLibraryOwner = new MessageToLibraryOwner(contentForLibraryOwner, MessageUtils.MESSAGE_RESERV_BOOK_TO_LIBRARY_OWNER_TITLE, book.getLibrary());
                messageToLibraryOwner = messageToLibraryOwnerRepository.save(messageToLibraryOwner);
                messageToCasualUser = new MessageToCasualUser(contentForCasualUser, MessageUtils.MESSAGE_RESERV_BOOK_TO_CASUAL_USER_TITLE, userService.findLoggedUser(), taskForUser);
                messageToLibraryOwnerRepository.save(messageToLibraryOwner);
                messageToCasualUserRepository.save(messageToCasualUser);
                log.info("[Reserv book]=" + book);

            }
            Date date;
            if (this.deploy == -1) {
                date = DataUtils.convertToDateViaInstant(taskForUser.getDateExpiration());
            } else {
                date = DataUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(this.deploy));
            }
            receiveTheBookForUserService.receiveTheBook(date, taskForUser.getBook().getID(), taskForUser.getUuid());

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity deleteReservBook(UUID bookId) {
        try {
            Book reservBook = bookRepository.findById(bookId).orElse(null);
            TaskForUser taskForUser = taskForUserRepository.findByBookAndTaskStatus(reservBook, TaskStatus.TO_DO);
            receiveTheBookForUserService.deleteJob(taskForUser.getUuid());
            taskForUser.setDateDone(LocalDateTime.now());
            taskForUser.setTaskStatus(TaskStatus.REMOVED);
            taskForUser = taskForUserRepository.save(taskForUser);
            String content = "Uzytkownik " + userService.findLoggedUser().getEmail() + " zrezygnował z rezerwacji ksiazki " + reservBook.getTitle() + " " + reservBook.getAuthor()+ " w bibliotece "+reservBook.getLibrary().getName()+".";
            MessageToLibraryOwner messageToLibraryOwner = new MessageToLibraryOwner(content,MessageUtils.MESSAGE_RESIGNATION_RESERV_BOOK_TO_LIBRARY_OWNER_TITLE, reservBook.getLibrary() );
            messageToLibraryOwnerRepository.save(messageToLibraryOwner);
            Book notReservBook = bookRepository.findFirstByAuthorAndTitleAndPublisherAndDateAndLibraryAndBookState(reservBook.getAuthor(), reservBook.getTitle(), reservBook.getPublisher(), reservBook.getDate(), reservBook.getLibrary(), BookState.NOTRESERVED);
            if (notReservBook != null) {

                reservBook.setBookState(BookState.DELETE);
                reservBook.setUserCasual(null);
                bookRepository.save(reservBook);
                notReservBook.setQuant(reservBook.getQuant() + notReservBook.getQuant());
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


    public ResponseEntity acceptReserv(UUID bookId) {
        try {

            Book book = bookRepository.findById(bookId).orElse(null);
            TaskForUser taskForUser = taskForUserRepository.findByBookAndTaskStatus(book, TaskStatus.TO_DO);
            User user=userRepository.findByUserCasual(book.getUserCasual());
            receiveTheBookForUserService.deleteJob(taskForUser.getUuid());
            taskForUser.setTaskStatus(TaskStatus.DONE);
            taskForUser.setDateDone(LocalDateTime.now());
            taskForUser = taskForUserRepository.save(taskForUser);
            String content = "Biblioteka " + book.getLibrary().getName() + " potwierdziła twoją rezerwacje ksiazki " + book.getTitle() + " " + book.getAuthor();
            MessageToCasualUser messageToCasualUser = new MessageToCasualUser(content, MessageUtils.MESSAGE_ACCEPT_RESERV_BOOK_TO_CASUAL_USER_TITLE, user, taskForUser);
            messageToCasualUserRepository.save(messageToCasualUser);
            if (!book.getUserMenager().equals(userService.findLoggedUser().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            book.setBookState(BookState.CONFIRMED);
            book = bookRepository.save(book);
            TaskForUser tmp = new TaskForUser(user, LocalDateTime.now(), LocalDateTime.now().plusDays(14), TaskStatus.TO_DO, book, book.getLibrary(), TaskForUserType.GIVE_BOOK);
            tmp = taskForUserRepository.save(tmp);

            TaskForLibrary taskForLibrary = new TaskForLibrary(userService.findLoggedUser(), LocalDateTime.now(), LocalDateTime.now().plusDays(14), TaskStatus.TO_DO, book, book.getLibrary(), TaskForLibraryType.REMIND_TO_GIVE_BACK_THE_BOOK);
            taskForLibrary = taskForLibraryRepository.save(taskForLibrary);

            if (this.deploy == -1) {
                reminderOfGivingABookForLibraryService.reminderOfGivingABookForLibrary(DataUtils.convertToDateViaInstant(taskForLibrary.getDateExpiration()), taskForLibrary.getBook().getID(), taskForLibrary.getUuid());
                reminderOfGivingABookForUserService.reminderOfGivingABookForUser(DataUtils.convertToDateViaInstant(tmp.getDateExpiration()), tmp.getBook().getID(), tmp.getUuid());
            } else {
                reminderOfGivingABookForLibraryService.reminderOfGivingABookForLibrary(DataUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(this.deploy)), taskForLibrary.getBook().getID(), taskForLibrary.getUuid());
                reminderOfGivingABookForUserService.reminderOfGivingABookForUser(DataUtils.convertToDateViaInstant(LocalDateTime.now().plusSeconds(this.deploy)), tmp.getBook().getID(), tmp.getUuid());

            }
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity deleteReserv(UUID bookId) {
        try {
            Book book = bookRepository.findById(bookId).orElse(null);
            User user=userRepository.findByUserCasual(book.getUserCasual());
            if (!book.getUserMenager().equals(userService.findLoggedUser().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            TaskForUser taskForUser = taskForUserRepository.findByBookAndTaskStatus(book, TaskStatus.TO_DO);
            receiveTheBookForUserService.deleteJob(taskForUser.getUuid());
            taskForUser.setTaskStatus(TaskStatus.REMOVED);
            taskForUser.setDateDone(LocalDateTime.now());
            taskForUserRepository.save(taskForUser);

            Book notReservBook = bookRepository.findFirstByAuthorAndTitleAndPublisherAndDateAndLibraryAndBookState(book.getAuthor(), book.getTitle(), book.getPublisher(), book.getDate(), book.getLibrary(), BookState.NOTRESERVED);

            if (notReservBook != null) {
                book.setBookState(BookState.DELETE);
                book.setUserCasual(null);
                bookRepository.save(book);
                notReservBook.setQuant(book.getQuant() + notReservBook.getQuant());
                bookRepository.save(notReservBook);
            } else {
                book.setBookState(BookState.NOTRESERVED);
                book.setUserCasual(null);
                bookRepository.save(book);
            }

            String content = "Biblioteka " + book.getLibrary().getName() + " odrzuciła twoją rezerwacje ksiazki " + book.getTitle() + " " + book.getAuthor();
            MessageToCasualUser messageToCasualUser = new MessageToCasualUser(content, MessageUtils.MESSAGE_REJECTED_RESERV_BOOK_TO_CASUAL_USER_TITLE, user, taskForUser);
            messageToCasualUserRepository.save(messageToCasualUser);

            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity returnBook(UUID bookId) {
        try {
            UUID taskForUserId;
            UUID taskForLibraryId;
            Book reservBook = bookRepository.findById(bookId).orElse(null);
            Book notReservBook = bookRepository.findFirstByAuthorAndTitleAndPublisherAndDateAndLibraryAndBookState(reservBook.getAuthor(), reservBook.getTitle(), reservBook.getPublisher(), reservBook.getDate(), reservBook.getLibrary(), BookState.NOTRESERVED);
            TaskForLibrary taskForLibrary = taskForLibraryRepository.findByBookAndTaskStatus(reservBook, TaskStatus.TO_DO);
            taskForLibraryId = taskForLibrary.getUuid();
            TaskForUser taskForUser = taskForUserRepository.findByBookAndTaskStatus(reservBook, TaskStatus.TO_DO);
            taskForUserId = taskForUser.getUuid();
            taskForLibrary.setTaskStatus(TaskStatus.DONE);
            taskForUser.setTaskStatus(TaskStatus.DONE);
            taskForLibrary.setDateDone(LocalDateTime.now());
            taskForUser.setDateDone(LocalDateTime.now());
            reminderOfGivingABookForLibraryService.deleteJob(taskForLibrary.getUuid());
            reminderOfGivingABookForUserService.deleteJob(taskForUser.getUuid());
            taskForLibraryRepository.save(taskForLibrary);
            taskForUserRepository.save(taskForUser);
            User user=userRepository.findByUserCasual(reservBook.getUserCasual());
            String contentMessageToLibraryOwner = "Uzytkownik " + user.getEmail()+ " oddał książke " + reservBook.getTitle() + " " + reservBook.getAuthor() + " do biblioteki" + reservBook.getLibrary().getName();
            String contentMessageToCasualUser = "Oddałeś książkę " + reservBook.getTitle() + " " + reservBook.getAuthor() + "do biblioteki" + reservBook.getLibrary().getName()+".";
            MessageToLibraryOwner messageToLibraryOwner = new MessageToLibraryOwner(contentMessageToLibraryOwner,MessageUtils.RETURN_BOOK_TO_LIBRARY_OWNER_TITLE,reservBook.getLibrary(),taskForLibrary);
            MessageToCasualUser messageToCasualUser = new MessageToCasualUser(contentMessageToCasualUser, MessageUtils.RETURN_BOOK_TO_CASUAL_USER_TITLE,user,taskForUser);
            messageToCasualUserRepository.save(messageToCasualUser);
            messageToLibraryOwnerRepository.save(messageToLibraryOwner);

            if (!reservBook.getUserMenager().equals(userService.findLoggedUser().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }

            if (notReservBook != null) {

                reservBook.setBookState(BookState.DELETE);
                reservBook.setUserCasual(null);
                bookRepository.save(reservBook);
                notReservBook.setQuant(reservBook.getQuant() + notReservBook.getQuant());
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


    public ResponseEntity getAllBooks(int page, int size) {
        try {
            Pageable pageableRequest = new PageRequest(page, size);
            Page<Book> bookList = bookRepository.findAll(pageableRequest);
            return ResponseEntity.ok(bookList);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }


}
