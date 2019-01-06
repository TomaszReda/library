package pl.tomekreda.library.model.task;

import lombok.Data;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name="TASK_TYPE",discriminatorType=DiscriminatorType.STRING)
public abstract class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID uuid;

    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private User user;

    private LocalDateTime dateCreate;

    private LocalDateTime dateExpiration;

    @Enumerated(value = EnumType.STRING)
    private TaskStatus taskStatus;

    @JoinColumn(name = "book_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Book book;

    @JoinColumn(name = "library_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Library library;


}
