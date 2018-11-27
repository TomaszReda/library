package pl.tomekreda.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class School {

    @Id
    @GeneratedValue
    private UUID ID;

    private String schoolName;


    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL)
    private List<Person> personList;


    @OneToOne(cascade = CascadeType.ALL)
    private Director director;

    public School(String schoolName) {
        this.schoolName = schoolName;
    }
}
