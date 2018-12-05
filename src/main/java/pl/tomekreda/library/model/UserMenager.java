package pl.tomekreda.library.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class UserMenager implements Serializable {

    @Id
    @GeneratedValue
    private Long ID;

    @OneToMany(mappedBy = "userMenager", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Library> libraryList = new ArrayList<>();

    public UserMenager() {

    }

    @Override
    public String toString() {
        return "UserMenager{" +
                "ID=" + ID +
                '}';
    }
}


