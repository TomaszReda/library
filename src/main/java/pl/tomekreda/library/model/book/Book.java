package pl.tomekreda.library.model.book;

import lombok.Data;
import pl.tomekreda.library.model.library.Library;

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

    @ManyToOne(cascade = CascadeType.ALL)
    private Library library;

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


}
