package pl.tomekreda.library.request;

import lombok.Data;

import java.util.UUID;

@Data
public class ActivationUserRequest {
    UUID activationToken;
}
