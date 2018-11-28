package pl.tomekreda.library.model.user;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
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


