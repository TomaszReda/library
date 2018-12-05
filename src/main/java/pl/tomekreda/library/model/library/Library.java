package pl.tomekreda.library.model.library;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserMenager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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

    @OneToMany(mappedBy = "library",cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Book> bookList=new ArrayList<>();


    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserMenager userMenager;

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

    @Override
    public String toString() {
        return "Library{" +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", latitude='" + latitude + '\'' +
                ", local='" + local + '\'' +
                ", longitude='" + longitude + '\'' +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", street='" + street + '\'' +
                ", userMenager=" + userMenager +
                '}';
    }
}
