package pl.tomekreda.library.request;

import lombok.Data;

@Data
public class ChangePasswordRequest {

    private String oldpassword;

    private String newpassword;

    private  String newpasswordrepeat;
}
