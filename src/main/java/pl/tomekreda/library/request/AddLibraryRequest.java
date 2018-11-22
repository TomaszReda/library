package pl.tomekreda.library.request;

import lombok.Data;

@Data
public class AddLibraryRequest {

    private String city;
    private String email;
    private String latitude;
    private String local;
    private String longitude;
    private String name;
    private String number;
    private String postalCode;
    private String street;
}
