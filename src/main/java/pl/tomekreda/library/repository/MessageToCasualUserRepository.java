package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.message.MessageToCasualUser;
import pl.tomekreda.library.model.user.User;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface MessageToCasualUserRepository extends JpaRepository<MessageToCasualUser, UUID> {

    List<MessageToCasualUser> findAllByUserAndDateReadIsNullOrderByDataCreateDesc(User user);

    List<MessageToCasualUser> findAllByDateReadIsNull();
}
