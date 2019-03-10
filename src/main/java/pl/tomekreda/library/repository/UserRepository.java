package pl.tomekreda.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserCasual;
import pl.tomekreda.library.model.user.UserMenager;

import java.util.UUID;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByEmail(String email);

    User findAllByUserMenager(UserMenager userMenager);

    Page<User> findAllByEmailContains(String word,Pageable pageable);

    User findUserByResetPasswordToken(UUID uuid);

    User findByUserCasual(UserCasual userCasual);
}
