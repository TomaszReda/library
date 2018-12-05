package pl.tomekreda.library.model.book;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class BookCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private String categoryType;

    public BookCategory(String categoryType) {
        this.categoryType = categoryType;
    }

    @OneToMany(mappedBy = "bookCategory", cascade = CascadeType.ALL)
    private List<Book> bookList = new ArrayList<>();
}
