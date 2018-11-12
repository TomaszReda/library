package pl.tomekreda.library.request;

import lombok.Data;

@Data
public class AddUserLibraryOwnerRequest {

    private String firstname;

    private String lastname;

    private String email;

    private int phoneNumber;

    private String password;

}
