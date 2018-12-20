package pl.tomekreda.library.profiles;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookCategory;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.*;
import pl.tomekreda.library.repository.BookCategoryRepository;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Component
@TestProfile
@DevProfile
@Transactional
@FirstRunProfile
public class TestingDevData implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookCategoryRepository bookCategoryRepository;

    @Override
    public void run(String... args) throws Exception {
        this.createBookCategory();

        User casual = new User("Kasia", "Reda", "tomekreda@op.pl", 123456789, passwordEncoder.encode("password"));
        UserRoles userCasualRole = new UserRoles();
        userCasualRole.setUserRole(UserRoleEnum.CASUAL_USER);
        UserCasual userCasual=new UserCasual();
        casual.setUserCasual(userCasual);
        casual.getUserRoles().add(userCasualRole);
        userRepository.save(casual);

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

        addLibrary(owner2);




    }


    private void createBookCategory() {

        BookCategory bookCategory = new BookCategory("Fantasy");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Biografie/Autobiografie");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Młodzieżowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Naukowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Sportowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Bajka");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Historyczna");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Horror");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Przygodowa");
        bookCategoryRepository.save(bookCategory);

        bookCategory = new BookCategory("Inna");
        bookCategoryRepository.save(bookCategory);

    }

    private void createBook(Library library, User owner) {
        BookCategory bookCategory = bookCategoryRepository.findFirstByCategoryType("Przygodowa");
        BookCategory bookCategory1 = bookCategoryRepository.findFirstByCategoryType("Fantasy");
        Book book = new Book("Henryk Sienkiewicz", "W pustyni i w puszczy", "PWD", LocalDate.of(1992, 12, 11), "12342", 1,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());

        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);
        book = new Book("Henryk Sienkiewicz", " sad asd ad adsa dasd ada", "PWD", LocalDate.of(1992, 12, 11), "12342", 1,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());

        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);
        book = new Book("Jakub Żulczyk", "test Ślepnąc od świateł", "ZNAK", LocalDate.of(1996, 10, 11), "132322", 6,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());

        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Jakub Żulczyk", "Ślepnąc od świateł", "PWD", LocalDate.of(1998, 10, 11), "124563", 3,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Rafał Wicijowski ", "Oczami Mężczyzny", "PZWL", LocalDate.of(1999, 2, 3), "5123", 1,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Rafał Wicijowski ", "Oczami Mężczyzny", "jaguar", LocalDate.of(2006, 3, 7), "32212", 9,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Blanka Lipińska ", "Ten dzień", "PWD", LocalDate.of(2005, 3, 5), "123422", 7,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Blanka Lipińska ", "Ten dzień", "Znak", LocalDate.of(2008, 9, 1), "123432", 3,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Colleen Hoover ", "Wszystkie nasze obietnice", "ZNAK", LocalDate.of(1992, 9, 6), "123432", 1,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Colleen Hoover ", "Wszystkie nasze obietnice", "PWD", LocalDate.of(1998, 10, 3), "123242", 2,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Colleen Hoover ", "Wszystkie nasze obietnice", "PZWL", LocalDate.of(1995, 12, 2), "123342", 3,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1996, 10, 3), "123422", 11,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1995, 8, 2), "123432", 12,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);


        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1996, 10, 3), "123422", 11,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1995, 8, 2), "123432", 12,"Opis",library,BookState.NOTRESERVED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1996, 10, 3), "123422", 11,"Opis",library,BookState.DELETE,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1995, 8, 2), "123432", 12,"Opis",library,BookState.DELETE,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        User user=userRepository.findUserByEmail("tomekreda@op.pl");

        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1996, 10, 3), "123422", 11,"Opis",library,BookState.CONFIRMED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserCasual(user.getUserCasual());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1995, 8, 2), "123432", 12,"Opis",library,BookState.CONFIRMED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserCasual(user.getUserCasual());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);


        book = new Book("Blanka Lipińska ", "Ten dzień", "PWD", LocalDate.of(2005, 3, 5), "123422", 7,"Opis",library,BookState.BOOKED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserCasual(user.getUserCasual());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);

        book = new Book("Blanka Lipińska ", "Ten dzień", "Znak", LocalDate.of(2008, 9, 1), "123432", 3,"Opis",library,BookState.BOOKED,null,bookCategory);
        book.setBookSearch(book.getAuthor()+" "+book.getTitle()+" "+book.getAuthor());
        book.setUserCasual(user.getUserCasual());
        book.setUserMenager(owner.getUserMenager());
        bookRepository.save(book);





    }


    private void addLibrary(User owner) {
        Library library = new Library("Chrustne", "tomekreda@op.pl", "51.61308", null, "21.97838", "Marzenie", "34", "08-500 Ryki", null);
        library.setUserMenager(owner.getUserMenager());
        library = libraryRepository.save(library);
//        for(int i=0;i<10;i++){
        this.createBook(library, owner);
//        }



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


