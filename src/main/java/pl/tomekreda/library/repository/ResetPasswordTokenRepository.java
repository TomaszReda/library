package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.user.ResetPasswordToken;

import java.util.UUID;

@RepositoryRestResource
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken, Long> {

   ResetPasswordToken findByResetToken(UUID resetToken);
}
