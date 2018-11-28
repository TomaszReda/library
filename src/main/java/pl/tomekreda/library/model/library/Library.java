package pl.tomekreda.library.model.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserMenager;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name = "library")
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

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private  UserMenager userMenager;

    public Library(String city, String email, String latitude, String local, String longitude, String name, String number, String postalCode, String street) {
        this.city = city;
        this.email = email;
        this.latitude = latitude;
        this.local = local;
        this.longitude = longitude;
        this.name = name;
        this.number = number;
        this.postalCode = postalCode;
        this.street = street;
    }

    public Library() {
    }
}
