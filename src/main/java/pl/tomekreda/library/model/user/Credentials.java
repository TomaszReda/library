package pl.tomekreda.library.model.user;

import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Credentials {
    private String email;
    private String password;
}
