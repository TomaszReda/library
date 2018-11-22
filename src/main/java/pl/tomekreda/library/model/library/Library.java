package pl.tomekreda.library.model.library;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "library")
public class Library {

    @Id
    @GeneratedValue
    private UUID ID;

    private String city;
    private String email;
    private String latitude;
    private String local;
    private String longitude;
    private String name;
    private String number;
    private String postalCode;
    private String street;
}
