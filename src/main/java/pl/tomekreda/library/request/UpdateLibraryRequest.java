package pl.tomekreda.library.request;

import lombok.Data;

import java.util.UUID;

@Data
public class UpdateLibraryRequest {

    private UUID libraryID;
    private String city;
    private String email;
    private String latitude;
    private String local;
    private String longitude;
    private String name;
    private String number;
    private String postalCode;
    private String street;
    private UUID userID;
}
