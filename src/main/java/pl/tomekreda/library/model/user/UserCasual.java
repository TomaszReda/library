package pl.tomekreda.library.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pl.tomekreda.library.model.book.Book;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UserCasual implements Serializable {

    @Id
    @GeneratedValue
    private Long ID;

    @OneToMany(mappedBy = "userCasual",cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Book> bookList = new ArrayList<>();

}
