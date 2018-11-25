package pl.tomekreda.library.profiles;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserRoleEnum;
import pl.tomekreda.library.model.user.UserRoles;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;

import java.util.UUID;

@Component
@TestProfile
@DevProfile
public class TestingDevData implements CommandLineRunner {

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        @Autowired
        private LibraryRepository libraryRepository;




    @Override
    public void run(String... args) throws Exception {
        User owner = new User("Tomek", "Reda", "owner@local", 123456789, passwordEncoder.encode("password"), null, null);
        UserRoles userOwnerRole = new UserRoles();
        userOwnerRole.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        owner.getUserRoles().add(userOwnerRole);
        userRepository.save(owner);

        Library library = new Library(null, "Chrustne", "tomekreda@op.pl", "51.61308", null, "21.97838", "Marzenie", "34", "08-500 Ryki", null, owner);
        libraryRepository.save(library);

        Library library2 = new Library(null, "Warszawa", "tomekreda@op.pl", "52.2631523", "101", "21.0288848266558", "Ksiazeczka", "11", "05-077 Warszawa", "Józefa Szanajcy", owner);
        libraryRepository.save(library2);

        User owner2 = new User("Tomek", "Reda", "owner2@local", 123456789, passwordEncoder.encode("password"), null, null);
        UserRoles userOwner2Role = new UserRoles();
        userOwner2Role.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        owner2.getUserRoles().add(userOwner2Role);
        userRepository.save(owner2);


        User casual = new User("Kasia", "Reda", "worker@local", 123456789, passwordEncoder.encode("password"), null, null);
        UserRoles userCasualRole = new UserRoles();
        userCasualRole.setUserRole(UserRoleEnum.CASUAL_USER);
        casual.getUserRoles().add(userCasualRole);
        userRepository.save(casual);
    }
}
