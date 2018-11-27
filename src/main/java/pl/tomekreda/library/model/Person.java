package pl.tomekreda.library.model;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@ToString
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long ID;

    private String personName;

    @ManyToOne(cascade = CascadeType.ALL)
    private School school;

    public Person(String personName) {
        this.personName = personName;
    }
}
