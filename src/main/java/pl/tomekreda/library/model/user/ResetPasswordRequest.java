package pl.tomekreda.library.model.user;

import lombok.Data;

import java.util.UUID;

@Data
public class ResetPasswordRequest {

    UUID resetToken;
}
