package pl.tomekreda.library.model.user;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import pl.tomekreda.library.model.library.Library;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@Entity
@AllArgsConstructor
@Table(name = "`user`")
public class User {


    @Id
    @GeneratedValue
    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private int phoneNumber;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private UserMenager userMenager;

    @OneToOne(cascade = CascadeType.ALL)
    private UserCasual userCasual;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserRoles> userRoles = new ArrayList<>();

    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL)
    private List<Library> libraries;

    public User() {
    }

    public User(String firstname, String lastname, String email, int phoneNumber, String password, UserMenager userMenager, UserCasual userCasual) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userMenager = userMenager;
        this.userCasual = userCasual;
    }
}
