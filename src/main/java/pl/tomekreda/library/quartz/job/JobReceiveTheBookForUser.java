package pl.tomekreda.library.quartz.job;

import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.task.TaskForUser;
import pl.tomekreda.library.model.task.TaskStatus;
import pl.tomekreda.library.quartz.service.ReceiveTheBookForUserService;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.TaskForUserRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
public class JobReceiveTheBookForUser implements Job {

    @Autowired
    private ReceiveTheBookForUserService receiveTheBookForUserService;

    @Autowired
    private TaskForUserRepository taskForUserRepository;

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("[Quartz JobReceiveTheBookForUser run with time]"+LocalDateTime.now().toString());
        JobDataMap detail = context.getJobDetail().getJobDataMap();
        UUID bookId = (UUID) detail.get("bookId");
        UUID taskForUserId = (UUID) detail.get("taskForUserId");
        Book reservBook = bookRepository.findById(bookId).orElse(null);
        TaskForUser taskForUser = taskForUserRepository.findById(taskForUserId).orElse(null);
        if (taskForUser != null) {
            if (taskForUser.getTaskStatus().equals(TaskStatus.TO_DO)) {
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

    }
}
