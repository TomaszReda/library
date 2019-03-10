package pl.tomekreda.library.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.tomekreda.library.model.book.Book;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserCasual  {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "userCasual",cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Book> bookList = new ArrayList<>();

}
