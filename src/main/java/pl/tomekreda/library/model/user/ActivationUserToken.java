package pl.tomekreda.library.model.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
public class ActivationUserToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "activationUserToken", cascade = CascadeType.ALL)
    private User user;

    @Type(type="org.hibernate.type.UUIDCharType")
    private UUID activeToken;

    private LocalDateTime expireTime;

    public ActivationUserToken(User user, UUID activeToken, LocalDateTime expireTime) {
        this.user = user;
        this.activeToken = activeToken;
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "ActivationUserToken{" +
                "id=" + id +
                ", activeToken=" + activeToken +
                ", expireTime=" + expireTime +
                '}';
    }
}
