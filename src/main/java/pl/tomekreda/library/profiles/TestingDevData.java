package pl.tomekreda.library.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.Director;
import pl.tomekreda.library.model.Person;
import pl.tomekreda.library.model.School;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.PersonRepository;
import pl.tomekreda.library.repository.SchoolRepository;
import pl.tomekreda.library.repository.UserRepository;

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


    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private PersonRepository personRepository;

    @Override
    public void run(String... args) throws Exception {

        Person person = new Person("person");
        School school = new School("school");
        Director director = new Director("director");




            person.setSchool(school);
            person = personRepository.save(person);
            System.err.println("\n"+person);

            school.setDirector(director);
            System.err.print(school);
            school= schoolRepository.save(school);
            System.err.println(school);


            System.err.println("\n"+school);
    }
}


//
//    User owner = new User("Tomek", "Reda", "owner@local", 123456789, passwordEncoder.encode("password"), null, null);
//    UserRoles userOwnerRole = new UserRoles();
//        userOwnerRole.setUserRole(UserRoleEnum.LIBRARY_OWNER);
//                owner.getUserRoles().add(userOwnerRole);
//                userRepository.save(owner);
//
//                Library library = new Library(null, "Chrustne", "tomekreda@op.pl", "51.61308", null, "21.97838", "Marzenie", "34", "08-500 Ryki", null, owner);
//                libraryRepository.save(library);
//
//                Library library2 = new Library(null, "Warszawa", "tomekreda@op.pl", "52.2631523", "101", "21.0288848266558", "Ksiazeczka", "11", "05-077 Warszawa", "Józefa Szanajcy", owner);
//                libraryRepository.save(library2);
//
//                User owner2 = new User("Tomek", "Reda", "owner2@local", 123456789, passwordEncoder.encode("password"), null, null);
//                UserRoles userOwner2Role = new UserRoles();
//                userOwner2Role.setUserRole(UserRoleEnum.LIBRARY_OWNER);
//                owner2.getUserRoles().add(userOwner2Role);
//                userRepository.save(owner2);
//
//
//                User casual = new User("Kasia", "Reda", "worker@local", 123456789, passwordEncoder.encode("password"), null, null);
//                UserRoles userCasualRole = new UserRoles();
//                userCasualRole.setUserRole(UserRoleEnum.CASUAL_USER);
//                casual.getUserRoles().add(userCasualRole);
//                userRepository.save(casual);