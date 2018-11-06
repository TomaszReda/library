package pl.tomekreda.library.model.user;

import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.io.Serializable;
import java.util.UUID;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(name = "`user`")
public class User {


    @Id
    @GeneratedValue
    private UUID id;

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    private String password;

    @OneToOne(cascade = CascadeType.ALL)
    private UserMenager userMenager;

    @OneToOne(cascade = CascadeType.ALL)
    private UserCasual userCasual;

    public User(String firstname, String lastname, String email, String phoneNumber, String password, UserMenager userMenager, UserCasual userCasual) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userMenager = userMenager;
        this.userCasual = userCasual;
    }

    public User() {

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", userMenager=" + userMenager +
                ", userCasual=" + userCasual +
                '}';
    }
}
