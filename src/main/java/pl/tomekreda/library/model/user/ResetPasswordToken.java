package pl.tomekreda.library.model.user;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResetPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ID;

    private UUID resetToken;

    @OneToOne(mappedBy = "resetPasswordToken",cascade = CascadeType.ALL)
    private User user;

    private LocalDateTime expireTime;

    public ResetPasswordToken(UUID resetToken, LocalDateTime expireTime) {
        this.resetToken = resetToken;
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "ResetPasswordToken{" +
                "ID=" + ID +
                ", activationToken=" + resetToken +
                ", expireTime=" + expireTime +
                '}';
    }
}
