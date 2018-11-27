package pl.tomekreda.library.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Data
@Entity
public class Director {


    @Id
    @GeneratedValue
    private UUID ID;

    private String directorName;

    public Director(String directorName) {
        this.directorName = directorName;
    }
}
