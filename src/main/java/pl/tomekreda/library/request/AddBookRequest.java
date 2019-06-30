package pl.tomekreda.library.request;

import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
public class AddBookRequest {

    private UUID libraryId;
    private String author;
    private String title;
    private String publisher;
    private LocalDate date;
    private String isbn;
    private int quant;
    private String description;
    private String bookCategory;

    public AddBookRequest(UUID libraryId, String author, String title, String publisher, LocalDate date, String isbn, int quant, String description, String bookCategory) {
        this.libraryId = libraryId;
        this.author = author;
        this.title = title;
        this.publisher = publisher;
        this.date = date;
        this.isbn = isbn;
        this.quant = quant;
        this.description = description;
        this.bookCategory = bookCategory;
    }

    public AddBookRequest() {
    }
}
