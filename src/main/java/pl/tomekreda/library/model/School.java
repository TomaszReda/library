package pl.tomekreda.library.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@ToString
public class School {

    @Id
    @GeneratedValue
    Long ID;

    private String schoolName;


    @OneToMany(mappedBy = "school", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Person> personList=new ArrayList<>();


    @OneToOne(cascade = CascadeType.ALL)
    private Director director;

    public School(String schoolName) {
        this.schoolName = schoolName;
    }
}
