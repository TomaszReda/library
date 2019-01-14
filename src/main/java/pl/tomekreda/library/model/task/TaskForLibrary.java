package pl.tomekreda.library.model.task;

import lombok.*;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class TaskForLibrary extends Task {

    @Enumerated(value = EnumType.STRING)
    private TaskForLibraryType taskForLibraryType;

    @OneToOne(mappedBy = "taskForLibrary")
    private MessageToLibraryOwner messageToLibraryOwner;


    public TaskForLibrary(User toUser, LocalDateTime dateCreate, LocalDateTime dateExpiration, TaskStatus taskStatus, Book book, Library library, TaskForLibraryType taskForLibraryType) {
        super(toUser, dateCreate, dateExpiration, taskStatus, book, library);
        this.taskForLibraryType = taskForLibraryType;
    }

    public TaskForLibrary(User user, LocalDateTime dateCreate, LocalDateTime dateExpiration, TaskStatus taskStatus, Book book, Library library, TaskForLibraryType taskForLibraryType, LocalDateTime dateDone) {
        super(user, dateCreate, dateExpiration, taskStatus, book, library, dateDone);
        this.taskForLibraryType = taskForLibraryType;
    }

    @Override
    public String toString() {
        return "TaskForLibrary{" +
                super.toString() +
                "taskForLibraryType=" + taskForLibraryType +
                ", messageToLibraryOwner=" + messageToLibraryOwner +
                '}';
    }
}
