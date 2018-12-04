package pl.tomekreda.library.model.book;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.UserCasual;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Data
public class Book {

    @Id
    @GeneratedValue
    private UUID ID;

    private String author;
    private String title;
    private String publisher;
    private LocalDate date;
    private String ISBN;
    private int quant;

    @Column(length = 4096)
    private String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private Library library;

    @Enumerated(EnumType.STRING)
    private BookState bookState;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserCasual userCasual;

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
                ", userCasual=" + userCasual +
                ", bookCategory=" + bookCategory +
                '}';
    }
}
