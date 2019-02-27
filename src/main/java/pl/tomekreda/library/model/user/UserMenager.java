package pl.tomekreda.library.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.library.Library;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserMenager {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "userMenager", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Library> libraryList = new ArrayList<>();

    @OneToMany(mappedBy = "userMenager", cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Book> bookList = new ArrayList<>();



    @Override
    public String toString() {
        return "UserMenager{" +
                "id=" + id +
                '}';
    }
}


