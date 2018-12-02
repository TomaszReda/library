package pl.tomekreda.library.request;

import lombok.Data;
import pl.tomekreda.library.model.book.Book;

import java.util.Date;
import java.util.UUID;

@Data
public class AddBookRequest {

    private UUID libraryId;
    private String author;
    private String title;
    private String publisher;
    private Date date;
    private String ISBN;
    private int quant;


}
