package pl.tomekreda.library.request;

import lombok.Data;

@Data
public class AddUserCasualRequest {

    private String firstname;

    private String lastname;

    private String email;

    private String phoneNumber;

    private String password;
}
