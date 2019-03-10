package pl.tomekreda.library.model.task;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "TASK_TYPE", discriminatorType = DiscriminatorType.STRING)
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Getter
@Setter
public class Task {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID uuid;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private User user;

    private LocalDateTime dateCreate;

    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDateTime dateExpiration;

    private LocalDateTime dateDone;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus taskStatus;

    @JoinColumn(name = "book_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Book book;

    @JoinColumn(name = "library_id")
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Library library;

    public Task(User user, LocalDateTime dateCreate, LocalDateTime dateExpiration, TaskStatus taskStatus, Book book, Library library) {
        this.user = user;
        this.dateCreate = dateCreate;
        this.dateExpiration = dateExpiration;
        this.taskStatus = taskStatus;
        this.book = book;
        this.library = library;
    }

    public Task(User user, LocalDateTime dateExpiration, TaskStatus taskStatus, Book book, Library library) {
        this.user = user;
        this.dateCreate = LocalDateTime.now();
        this.dateExpiration = dateExpiration;
        this.taskStatus = taskStatus;
        this.book = book;
        this.library = library;
    }

    @Override
    public String toString() {
        return "Task{" +
                "uuid=" + uuid +
                ", dateCreate=" + dateCreate +
                ", dateExpiration=" + dateExpiration +
                ", taskStatus=" + taskStatus +
                '}';
    }


}
