package pl.tomekreda.library.model.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import pl.tomekreda.library.model.library.Library;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class UserMenager implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    @OneToMany(mappedBy = "userMenager", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Library> libraryList = new ArrayList<>();

    public UserMenager() {

    }
}


