package pl.tomekreda.library.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ResetPasswordRequest {

    UUID resetToken;
}
