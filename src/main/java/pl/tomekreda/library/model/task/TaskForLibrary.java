package pl.tomekreda.library.model.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.user.User;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.OneToOne;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Getter
@Setter
public class TaskForLibrary extends Task {

    @Enumerated(value = EnumType.STRING)
    private TaskForLibraryType taskForLibraryType;

    @OneToOne(mappedBy = "taskForLibrary")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MessageToLibraryOwner messageToLibraryOwner;


    public TaskForLibrary(User toUser, LocalDateTime dateCreate, LocalDateTime dateExpiration, TaskStatus taskStatus, Book book, Library library, TaskForLibraryType taskForLibraryType) {
        super(toUser, dateCreate, dateExpiration, taskStatus, book, library);
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
