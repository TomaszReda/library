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


}
