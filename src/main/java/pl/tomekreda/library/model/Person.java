package pl.tomekreda.library.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class Person {

    @Id
    @GeneratedValue
    private UUID ID;

    private String personName;

    @ManyToOne
    private School school;

    public Person(String personName) {
        this.personName = personName;
    }
}
