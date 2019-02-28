package pl.tomekreda.library.model.library;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.task.Task;
import pl.tomekreda.library.model.user.UserMenager;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "library")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Library {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

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

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "library")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<Task> taskForLibrary;


    @ManyToOne
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private UserMenager userMenager;

    @OneToMany(mappedBy = "library",cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private List<MessageToLibraryOwner> messageToLibraryOwners = new ArrayList<>();





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
