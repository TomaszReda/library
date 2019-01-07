package pl.tomekreda.library.model.task;

import lombok.*;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.library.Library;
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


    public TaskForLibrary(User toUser, LocalDateTime dateCreate, LocalDateTime dateExpiration, TaskStatus taskStatus, Book book, Library library, TaskForLibraryType taskForLibraryType) {
        super(toUser, dateCreate, dateExpiration, taskStatus, book, library);
        this.taskForLibraryType = taskForLibraryType;
    }

}
