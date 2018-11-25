package pl.tomekreda.library.model.library;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tomekreda.library.model.user.User;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "library")
@NoArgsConstructor
@AllArgsConstructor
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

    @ManyToOne
    private User owner;


}
