package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.message.MessageDisplay;
import pl.tomekreda.library.model.message.MessageToCasualUser;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.task.TaskForLibrary;
import pl.tomekreda.library.model.task.TaskForUser;
import pl.tomekreda.library.model.task.TaskStatus;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.quartz.service.ReceiveTheBookForUserService;
import pl.tomekreda.library.repository.*;
import pl.tomekreda.library.utils.MessageUtils;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class QuartzService {

    private final ReceiveTheBookForUserService receiveTheBookForUserService;

    private final TaskForUserRepository taskForUserRepository;

    private final TaskForLibraryRepository taskForLibraryRepository;

    private final BookRepository bookRepository;

    private final UserRepository userRepository;

    private final MessageToLibraryOwnerRepository messageToLibraryOwnerRepository;

    private final MessageToCasualUserRepository messageToCasualUserRepository;

    public void quartzJobReceiveTheBookForUser(UUID bookId, UUID taskForUserId) {
        Book reservBook = bookRepository.findById(bookId).orElse(null);
        if (reservBook == null) {
            return;
        }
        TaskForUser taskForUser = taskForUserRepository.findById(taskForUserId).orElse(null);
        User user = userRepository.findByUserCasual(reservBook.getUserCasual());
        String contentForCasualUser = " Nie odebrałeś w ciągu 3 dni zarezerwowanej książki " + reservBook.getAuthor() + " - " + reservBook.getTitle() + " z biblioteki" + reservBook.getLibrary().getName() + MessageUtils.IN_COUNT + +reservBook.getQuant() + ".";
        String contentForLibraryOwner = "Użytkownik " + user.getEmail() + " nie odebrał ksiazki w ciagu 3 dni zarezerwowanej ksiazki  " + reservBook.getAuthor() + "-  " + reservBook.getTitle() + " z biblioteki " + reservBook.getLibrary().getName() + MessageUtils.IN_COUNT + +reservBook.getQuant() + ".";
        MessageToCasualUser messageToCasualUser = new MessageToCasualUser(contentForCasualUser, MessageUtils.RESERV_NOT_RECEIVED_FOR_CASUAL_USER, user, null, MessageDisplay.DANGER);
        MessageToLibraryOwner messageToLibraryOwner = new MessageToLibraryOwner(contentForLibraryOwner, MessageUtils.RESERV_NOT_RECEIVED_FOR_LIBRARY_OWNER, reservBook.getLibrary(), MessageDisplay.DANGER);
        messageToCasualUserRepository.save(messageToCasualUser);
        messageToLibraryOwnerRepository.save(messageToLibraryOwner);
        if (taskForUser != null && taskForUser.getTaskStatus().equals(TaskStatus.TO_DO)) {

            taskForUser.setTaskStatus(TaskStatus.REMOVED);
            taskForUser.setDateDone(LocalDateTime.now());
            taskForUserRepository.save(taskForUser);
            Book notReservBook = bookRepository.findFirstByAuthorAndTitleAndPublisherAndDateAndLibraryAndBookState(reservBook.getAuthor(), reservBook.getTitle(), reservBook.getPublisher(), reservBook.getDate(), reservBook.getLibrary(), BookState.NOTRESERVED);
            if (notReservBook != null) {
                reservBook.setUserCasual(null);
                reservBook.setBookState(BookState.DELETE);
                bookRepository.save(reservBook);
                notReservBook.setQuant(reservBook.getQuant() + notReservBook.getQuant());
                bookRepository.save(notReservBook);

            } else {
                reservBook.setBookState(BookState.NOTRESERVED);
                reservBook.setUserCasual(null);
                bookRepository.save(reservBook);
            }

        }
    }


    public void quartzJobReminderOfGivingABookForLibrary(UUID bookId, UUID taskForLibraryId) {
        Book reservBook = bookRepository.findById(bookId).orElse(null);
        if (reservBook == null) {
            return;
        }
        TaskForLibrary taskForLibrary = taskForLibraryRepository.findById(taskForLibraryId).orElse(null);
        if (taskForLibrary == null) {
            return;
        }
        User user = userRepository.findByUserCasual(reservBook.getUserCasual());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDate = taskForLibrary.getDateExpiration().format(formatter);
        String content = "Termin oddania książki( " + formatDate + " ) " + reservBook.getTitle() + " - " + reservBook.getAuthor() + MessageUtils.IN_COUNT + +reservBook.getQuant() + " użytkownika " + user.getEmail() + " w bibliotece " + reservBook.getLibrary().getName() + " minął.";
        MessageToLibraryOwner messageToLibraryOwner = new MessageToLibraryOwner(content, MessageUtils.REMINDER_OF_GIVING_A_BOOK_FOR_LIBRARY_OWNER, reservBook.getLibrary(), taskForLibrary, MessageDisplay.WARNING);
        messageToLibraryOwnerRepository.save(messageToLibraryOwner);
    }


    public void quartzJobReminderOfGivingABookForUser(UUID bookId, UUID taskForUserId) {
        Book reservBook = bookRepository.findById(bookId).orElse(null);
        if (reservBook == null) {
            return;
        }
        TaskForUser taskForUser = taskForUserRepository.findById(taskForUserId).orElse(null);
        if (taskForUser == null) {
            return;
        }
        User user = userRepository.findByUserCasual(reservBook.getUserCasual());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String formatDate = taskForUser.getDateExpiration().format(formatter);
        String content = "Termin oddania książki( " + formatDate + " ) " + reservBook.getTitle() + " - " + reservBook.getAuthor() + MessageUtils.IN_COUNT + +reservBook.getQuant() + " w bibliotece " + reservBook.getLibrary().getName() + " minął.";
        MessageToCasualUser messageToCasualUser = new MessageToCasualUser(content, MessageUtils.REMINDER_OF_GIVING_A_BOOK_FOR_CASUAL_USER, user, taskForUser, MessageDisplay.WARNING);
        messageToCasualUserRepository.save(messageToCasualUser);
    }


}
