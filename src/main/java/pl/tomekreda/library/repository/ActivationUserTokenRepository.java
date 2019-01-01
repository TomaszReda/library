package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.user.ActivationUserToken;

import java.util.UUID;

@RepositoryRestResource
public interface ActivationUserTokenRepository  extends JpaRepository<ActivationUserToken,Long> {
    ActivationUserToken findByActiveToken(UUID activationToken);
}
