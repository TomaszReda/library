package pl.tomekreda.library.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.task.Task;
import pl.tomekreda.library.model.user.UserCasual;
import pl.tomekreda.library.model.user.UserMenager;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
public class Book {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID ID;

    private String author;
    private String title;
    private String publisher;
    private LocalDate date;
    private String ISBN;
    private int quant;

    private String bookSearch;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "book")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> task;

    @Column(length = 4096)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Library library;

    @Enumerated(EnumType.STRING)
    private BookState bookState;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserCasual userCasual;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserMenager userMenager;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private BookCategory bookCategory;

    public Book(String author, String title, String publisher, LocalDate date, String ISBN, int quant) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.date = date;
        this.ISBN = ISBN;
        this.quant = quant;
    }

    public Book(String author, String title, String publisher, LocalDate date, String ISBN, int quant, String description, Library library, BookState bookState, UserCasual userCasual, BookCategory bookCategory) {
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.date = date;
        this.ISBN = ISBN;
        this.quant = quant;
        this.description = description;
        this.library = library;
        this.bookState = bookState;
        this.userCasual = userCasual;
        this.bookCategory = bookCategory;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "ID=" + ID +
                ", author='" + author + '\'' +
                ", title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", date=" + date +
                ", ISBN='" + ISBN + '\'' +
                ", quant=" + quant +
                ", description='" + description + '\'' +
                ", bookState=" + bookState +
                '}';
    }
}
