package pl.tomekreda.library.model.book;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
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
    private Date date;
    private String ISBN;
    private int quant;
}
