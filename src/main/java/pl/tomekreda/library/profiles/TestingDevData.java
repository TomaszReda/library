package pl.tomekreda.library.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.model.user.UserMenager;
import pl.tomekreda.library.model.user.UserRoleEnum;
import pl.tomekreda.library.model.user.UserRoles;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;

import javax.transaction.Transactional;

@Component
@TestProfile
@DevProfile
@Transactional
public class TestingDevData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LibraryRepository libraryRepository;

    @Override
    public void run(String... args) throws Exception {

        User owner = new User("Tomek", "Reda", "owner@local", 123456789, passwordEncoder.encode("password"));
        UserRoles userOwnerRole = new UserRoles();
        userOwnerRole.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        owner.getUserRoles().add(userOwnerRole);
        UserMenager userMenager = new UserMenager();
        owner.setUserMenager(userMenager);
        owner = userRepository.save(owner);

        addLibrary(owner);

        User owner2 = new User("Tomek", "Reda", "owner2@local", 123456789, passwordEncoder.encode("password"));
        UserRoles userOwner2Role = new UserRoles();
        userOwner2Role.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        UserMenager userMenager2 = new UserMenager();
        owner2.setUserMenager(userMenager2);
        owner2.getUserRoles().add(userOwner2Role);
        userRepository.save(owner2);

        User casual = new User("Kasia", "Reda", "worker@local", 123456789, passwordEncoder.encode("password"));
        UserRoles userCasualRole = new UserRoles();
        userCasualRole.setUserRole(UserRoleEnum.CASUAL_USER);
        casual.getUserRoles().add(userCasualRole);
        userRepository.save(casual);


    }


    private void addLibrary(User owner) {
        Library library = new Library("Chrustne", "tomekreda@op.pl", "51.61308", null, "21.97838", "Marzenie", "34", "08-500 Ryki", null);
        library.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library);

        Library library2 = new Library("Warszawa", "tomekreda@op.pl", "52.2631523", "101", "21.0288848266558", "Ksiazeczka", "11", "05-077 Warszawa", "Józefa Szanajcy");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.2319237", null, "21.0067265", "Czytanko", "26", "02-512 Warszawa", "Puławska");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.165191", null, "21.0702468", "Ksiegarnius", "18", "02-972 Warszawa", "Aleja Rzeczypospolitej");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.1634313", null, "21.0408349358631", "Ksiegarnia PSW", "161", "02-097 Warszawa", "Nowoursynowska");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.2448274", null, "21.0147069", "Księgarnia PWNN", "62", "00-322 Warszawa", "Krakowskie Przedmieście");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);


        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.205177", null, "20.9390127330357", "Ksiegarnia medyczna", "2", "02-460 Warszawa", "Daimlera");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.19902895", null, "20.9983573717488", "Ksiegarnia prawnicza", "137", "02-507 Warszawa", "Wołoska");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.1929968", null, "21.0134542", "Klub książki", "47", "02-001 Warszawa", "Antoniego Malczewskiego ");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.1930164", null, "21.0123836", "Tarabuk", "52", "02-001 Warszawa", "Antoniego Malczewskiego\t");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);


        // add duplicat library
        //
        //
        library = new Library("Chrustne", "tomekreda@op.pl", "51.61308", null, "21.97838", "Marzenieee", "34", "08-500 Ryki", null);
        library.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.2631523", "101", "21.0288848266558", "Ksiazeczkaaa", "11", "05-077 Warszawa", "Józefa Szanajcy");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.2319237", null, "21.0067265", "Czytankooo", "26", "02-512 Warszawa", "Puławska");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.165191", null, "21.0702468", "Ksiegarniusss", "18", "02-972 Warszawa", "Aleja Rzeczypospolitej");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.1634313", null, "21.0408349358631", "Ksiegarnia PSWWW", "161", "02-097 Warszawa", "Nowoursynowska");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.2448274", null, "21.0147069", "Klub książki", "62", "00-322 Warszawa", "Krakowskie Przedmieście");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);


        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.205177", null, "20.9390127330357", "Ksiegarnia prawnicza", "2", "02-460 Warszawa", "Daimlera");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.19902895", null, "20.9983573717488", "Ksiegarnia medycznaAAA", "137", "02-507 Warszawa", "Wołoska");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.1929968", null, "21.0134542", "Księgarnia PWNNNN", "47", "02-001 Warszawa", "Antoniego Malczewskiego ");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);

        library2 = new Library("Warszawa", "tomekreda@op.pl", "52.1930164", null, "21.0123836", "Tarabuk", "52", "02-001 Warszawa", "Antoniego Malczewskiego\t");
        library2.setUserMenager(owner.getUserMenager());
        libraryRepository.save(library2);


    }


}


