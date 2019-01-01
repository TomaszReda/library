package pl.tomekreda.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserMenager;
import pl.tomekreda.library.model.user.UserRoleEnum;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByEmail(String email);

    User findAllByUserMenager(UserMenager userMenager);

    Page<User> findAllByEmailContains(String word,Pageable pageable);

    User findUserByResetPasswordToken(UUID uuid);
}
