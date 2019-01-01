package pl.tomekreda.library.model.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Data
@Entity
@Getter
@Setter
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
    private ActivationUserToken activationUserToken;

    @Enumerated(EnumType.STRING)
    private UserState userState;

    @OneToOne(cascade = CascadeType.ALL)
    private UserMenager userMenager;

    @OneToOne(cascade = CascadeType.ALL)
    private UserCasual userCasual;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private List<UserRoles> userRoles = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private ResetPasswordToken resetPasswordToken;


    public User() {
    }

    public User(String firstname, String lastname, String email, int phoneNumber, String password,UserState userState) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.userState=userState;
    }
}
