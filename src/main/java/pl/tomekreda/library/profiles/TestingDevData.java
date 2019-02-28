package pl.tomekreda.library.profiles;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookCategory;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.message.MessageDisplay;
import pl.tomekreda.library.model.message.MessageToCasualUser;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.task.TaskForUser;
import pl.tomekreda.library.model.task.TaskForUserType;
import pl.tomekreda.library.model.task.TaskStatus;
import pl.tomekreda.library.model.user.*;
import pl.tomekreda.library.profiles.util.TestingData;
import pl.tomekreda.library.profiles.util.TestingProfilesUtils;
import pl.tomekreda.library.repository.*;
import pl.tomekreda.library.utils.MessageUtils;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

@Component
@TestProfile
@DevProfile
@Transactional
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TestingDevData implements CommandLineRunner {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final LibraryRepository libraryRepository;

    private final BookRepository bookRepository;

    private final BookCategoryRepository bookCategoryRepository;

    private final TaskForUserRepository taskForUserRepository;

    private final MessageToLibraryOwnerRepository messageToLibraryOwnerRepository;

    private final MessageToCasualUserRepository messageToCasualUserRepository;

    private Random rn = new Random();

    private final TestingData testingData;

    @Override
    public void run(String... args) throws Exception {
        testingData.createBookCategory();

        User casual = new User("Kasiaa", "Reda", TestingProfilesUtils.EMAIL_TOMEK, 123456789, passwordEncoder.encode(TestingProfilesUtils.DATAP), UserState.ACTIVE);
        UserRoles userCasualRole = new UserRoles();
        userCasualRole.setUserRole(UserRoleEnum.CASUAL_USER);
        UserCasual userCasual = new UserCasual();
        casual.setUserCasual(userCasual);
        casual.getUserRoles().add(userCasualRole);
        userRepository.save(casual);

        User owner = new User("Tomekk", "Redaa", "owner@local", 123456789, passwordEncoder.encode(TestingProfilesUtils.DATAP), UserState.ACTIVE);
        UserRoles userOwnerRole = new UserRoles();
        userOwnerRole.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        owner.getUserRoles().add(userOwnerRole);
        UserMenager userMenager = new UserMenager();
        owner.setUserMenager(userMenager);
        owner = userRepository.save(owner);

        addLibrary(owner);

        User owner2 = new User("Tomekkkk", "Reada", "owner2@local", 123456789, passwordEncoder.encode(TestingProfilesUtils.DATAP), UserState.ACTIVE);
        UserRoles userOwner2Role = new UserRoles();
        userOwner2Role.setUserRole(UserRoleEnum.LIBRARY_OWNER);
        UserMenager userMenager2 = new UserMenager();
        owner2.setUserMenager(userMenager2);
        owner2.getUserRoles().add(userOwner2Role);
        userRepository.save(owner2);

        addLibrary(owner2);

        User admin = new User("Tomekkk", "Redaa", "admin@local", 123456789, passwordEncoder.encode(TestingProfilesUtils.DATAP), UserState.ACTIVE);
        UserRoles userAdminRole = new UserRoles();
        userAdminRole.setUserRole(UserRoleEnum.ADMIN);
        admin.getUserRoles().add(userAdminRole);
        userRepository.save(admin);

        testingData.createTemplate();

    }


    private void createBook(Library library, User owner) {
        BookCategory bookCategory = bookCategoryRepository.findFirstByCategoryType("Przygodowa");
        Book book = new Book(null, "Henryk Sienkiewicz", "W pustyni i w puszczy", "PWD", LocalDate.of(1992, 12, 11), "12213342", 1, "Henryk Sienkiewicz W pustyni i w puszczy", null, "Opis", library, BookState.NOTRESERVED, null, owner.getUserMenager(), bookCategory);
        bookRepository.save(book);

        createBook(new Book(null,"Henryk Sienkiewiczz", " sad asd ad adsa dasd ada", "PWD", LocalDate.of(1992, 12, 11), "12332142", 1,"Henryk Sienkiewiczz sad asd ad adsa dasd ada Henryk Sienkiewiczz",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Jakub Żulczykk", "test Ślepnąc od świateł", "ZNAK", LocalDate.of(1996, 10, 11), "13232322", 6, "Jakub Żulczykk test Ślepnąc od świateł Jakub Żulczykk ",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Jakub Żulczyk", "Ślepnąc od światełł", "PWD", LocalDate.of(1998, 10, 11), "12453263", 3, "Jakub Żulczyk Ślepnąc od światełł Jakub Żulczyk",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Rafał Wicijowski ", "Oczami Mężczyzny", "PZWL", LocalDate.of(1999, 2, 3), "532123", 1, "Rafał Wicijowski Oczami Mężczyzny  Rafał Wicijowski",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Rafał Wicijowskii ", "Oczami Mężczyznyy", "jaguar", LocalDate.of(2006, 3, 7), "32212312", 9, "Rafał Wicijowskii Oczami Mężczyznyy Rafał Wicijowskii",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Blanka Lipińska ", "Ten dzień", "PWD", LocalDate.of(2005, 3, 5), "123413223", 7, "Blanka Lipińska  Ten dzień  Blanka Lipińska ",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Blanka Lipińskaa ", "Ten dzieńń", "Znak", LocalDate.of(2008, 9, 1), "1234323122", 3, "Blanka Lipińskaa Ten dzień Blanka Lipińskaa",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Colleen Hoover ", "Wszystkie nasze obietnicee", "ZNAK", LocalDate.of(1992, 9, 6), "12343232145", 1, "Colleen Hoover Wszystkie nasze obietnicee Colleen Hoover",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Colleen Hooverr ", "Wszystkie nasze obietniceee", "PWD", LocalDate.of(1998, 10, 3), "123242212", 2, "Colleen Hooverr Wszystkie nasze obietnicee Colleen Hoover Colleen Hooverr",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Colleen Hooverrr ", "Wszystkie nasze obietniceeee", "PZWL", LocalDate.of(1995, 12, 2), "123343212", 3, "Colleen Hooverrr Wszystkie nasze obietniceeee Colleen Hooverrr",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Anna Todd ", "After. Płomień pod moją skórąaa", "PWD", LocalDate.of(1996, 10, 3), "123423242", 11, "Anna Todd After. Płomień pod moją skórąaa Anna Todd ",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Anna Toddd ", "After. Płomień pod moją skórąa", "PWD", LocalDate.of(1995, 8, 2), "123433212", 12, "Anna Toddd After. Płomień pod moją skórąa Anna Toddd",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Anna Todddd ", "After. Płomień pod moją skórąaaa", "PWD", LocalDate.of(1996, 10, 3), "1234232142", 11, "Anna Todddd After. Płomień pod moją skórąaaa Anna Todddd",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));


        createBook(new Book(null,"Anna Toddddddddd ", "After. Płomień podd moją skórą", "PWD", LocalDate.of(1995, 8, 2), "1234432532", 12, "Anna Toddddddddd After. Płomień podd moją skórą Anna Toddddddddd",null,"Opis", library, BookState.NOTRESERVED, null,owner.getUserMenager(),bookCategory));

        book = new Book(null, "Annaa Todddddd ", "After. Płomień pod mojąa skórą", "PWD", LocalDate.of(1996, 10, 3), "1234221532", 11, "Annaa Todddddd After. Płomień pod mojąa skórą Annaa Toddddddd", null, "Opis", library, BookState.DELETE, null, owner.getUserMenager(), bookCategory);
        bookRepository.save(book);

        book = new Book(null, "Annaa Toddddddd ", "After. Płomień pod mojąa skórąą", "PWD", LocalDate.of(1996, 10, 3), "1234221532", 11, "Annaa Todddddd After. Płomień pod mojąa skórą Annaa Todddddd", null, "Opiss", library, BookState.DELETE, null, owner.getUserMenager(), bookCategory);
        bookRepository.save(book);

        User user = userRepository.findUserByEmail(TestingProfilesUtils.EMAIL_TOMEK);

        TaskForUser taskForUser;

        String contentForCasualUser;
        String contentForLibraryOwner;

        MessageToCasualUser messageToCasualUser;
        MessageToLibraryOwner messageToLibraryOwner;

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        book = new Book(null, "Anna Todd ", "After. Płomień pod moją skórą", "PWD", LocalDate.of(1996, 10, 3), "123422", 11, "Anna Todd After. Płomień pod moją skórą Anna Todd ", null, "Opis", library, BookState.BOOKED, user.getUserCasual(), owner.getUserMenager(), bookCategory);
        book = bookRepository.save(book);
        taskForUser = new TaskForUser(user,  LocalDateTime.now().plusDays(14), TaskStatus.TO_DO, book, library, TaskForUserType.GET_THE_BOOK);
        taskForUserRepository.save(taskForUser);
        String formatDate = taskForUser.getDateExpiration().format(formatter);
        contentForCasualUser = "Zarezerwowałeś książke " + book.getTitle() + " - " + book.getAuthor() + " w ilosci " + book.getQuant() + " w bibliotece " + book.getLibrary().getName() + "." + " Masz 3 dni na jej odebranie( Termin mija " + formatDate + " ).";
        contentForLibraryOwner = " Użytkownik " + user.getEmail() + " zarezerwował twoja książke " + book.getTitle() + " - " + book.getAuthor() + " w bibliotecę " + book.getLibrary().getName() + ".";

        messageToLibraryOwner = new MessageToLibraryOwner(contentForLibraryOwner, MessageUtils.MESSAGE_RESERV_BOOK_TO_LIBRARY_OWNER_TITLE, book.getLibrary(), MessageDisplay.ALERT);
        messageToCasualUser = new MessageToCasualUser(contentForCasualUser, MessageUtils.MESSAGE_RESERV_BOOK_TO_CASUAL_USER_TITLE, user, taskForUser, MessageDisplay.ALERT);
        messageToCasualUserRepository.save(messageToCasualUser);
        messageToLibraryOwnerRepository.save(messageToLibraryOwner);

        book = new Book(null, "Anna Todd2 ", "After. Płomień pod moją skórą2", "PWD2", LocalDate.of(1995, 8, 2), "123432", 12, "Anna Todd2 After. Płomień pod moją skórą2 Anna Todd2", null, "Opis", library, BookState.BOOKED, user.getUserCasual(), owner.getUserMenager(), bookCategory);
        book = bookRepository.save(book);
        taskForUser = new TaskForUser(user,  LocalDateTime.now().plusDays(14), TaskStatus.TO_DO, book, library, TaskForUserType.GET_THE_BOOK);
        taskForUserRepository.save(taskForUser);
        contentForCasualUser = "Zarezerwowałeś książke " + book.getTitle() + " - " + book.getAuthor() + " w ilosci " + book.getQuant() + " w bibliotece " + book.getLibrary().getName() + "." + " Masz 3 dni na jej odebranie( Termin mija " + formatDate + " ).";
        contentForLibraryOwner = " Użytkownik " + user.getEmail() + " zarezerwował twoja książke " + book.getTitle() + " - " + book.getAuthor() + " w bibliotecę " + book.getLibrary().getName() + ".";
        messageToLibraryOwner = new MessageToLibraryOwner(contentForLibraryOwner, MessageUtils.MESSAGE_RESERV_BOOK_TO_LIBRARY_OWNER_TITLE, book.getLibrary(), MessageDisplay.ALERT);
        messageToCasualUser = new MessageToCasualUser(contentForCasualUser, MessageUtils.MESSAGE_RESERV_BOOK_TO_CASUAL_USER_TITLE, user, taskForUser, MessageDisplay.ALERT);
        messageToCasualUserRepository.save(messageToCasualUser);
        messageToLibraryOwnerRepository.save(messageToLibraryOwner);


    }


    private void createBook(Book book) {
        bookRepository.save(book);
    }

    private void addLibrary(User owner) {

        Library library = new Library(null, "Chrustne", TestingProfilesUtils.EMAIL_TOMEK, "51.61308", null, "21.97838", "Marzenie" + rn.nextInt(10) + 1, "34", "08-500 Ryki", null, null, null, owner.getUserMenager(), null);
        library = libraryRepository.save(library);
        this.createBook(library, owner);

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.2631523", "101", "21.0288848266558", "Ksiazeczka", "11", "05-077 Warszawa", "Józefa Szanajcy",null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.2319237", null, "21.0067265", "Czytanko", "26", "02-512 Warszawa", "Puławska", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.165191", null, "21.0702468", "Ksiegarnius", "18", "02-972 Warszawa", "Aleja Rzeczypospolitej", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.1634313", null, "21.0408349358631", "Ksiegarnia PSW", "161", "02-097 Warszawa", "Nowoursynowska", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.2448274", null, "21.0147069", "Księgarnia PWNN", "62", "00-322 Warszawa", "Krakowskie Przedmieście", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.205177", null, "20.9390127330357", "Ksiegarnia medyczna", "2", "02-460 Warszawa", "Daimlera", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.19902895", null, "20.9983573717488", "Ksiegarnia prawnicza", "137", "02-507 Warszawa", "Wołoska", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.1929968", null, "21.0134542", "Klub książki", "47", "02-001 Warszawa", "Antoniego Malczewskiego ", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.1930164", null, "21.0123836", "Tarabuk", "52", "02-001 Warszawaa", "Antoniego Malczewskiego\t", null,null,owner.getUserMenager(),null));

        library = new Library(null, "Chrustne", TestingProfilesUtils.EMAIL_TOMEK, "51.61308", null, "21.97838", "Marzenieee", "34", "08-500 Ryki", null, null, null, owner.getUserMenager(), null);
        libraryRepository.save(library);

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.2631523", "101", "21.0288848266558", "Ksiazeczkaaa", "11", "05-077 Warszawa", "Józefa Szanajcy", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.2319237", null, "21.0067265", "Czytankooo", "26", "02-512 Warszawa", "Puławska", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.165191", null, "21.0702468", "Ksiegarniusss", "18", "02-972 Warszawa", "Aleja Rzeczypospolitej", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.1634313", null, "21.0408349358631", "Ksiegarnia PSWWW", "161", "02-097 Warszawa", "Nowoursynowska", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.2448274", null, "21.0147069", "Klub książki", "62", "00-322 Warszawa", "Krakowskie Przedmieście", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.205177", null, "20.9390127330357", "Ksiegarnia prawnicza", "2", "02-460 Warszawa", "Daimlera", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.19902895", null, "20.9983573717488", "Ksiegarnia medycznaAAA", "137", "02-507 Warszawa", "Wołoska", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.1929968", null, "21.0134542", "Księgarnia PWNNNN", "47", "02-001 Warszawaaa", "Antoniego Malczewskiego ", null,null,owner.getUserMenager(),null));

        addOneLibrary(new Library(null,TestingProfilesUtils.CITY, TestingProfilesUtils.EMAIL_TOMEK, "52.1930164", null, "21.0123836", "Tarabuk", "52", "02-001 Warszawaaaaa", "Antoniego Malczewskiego\t", null,null,owner.getUserMenager(),null));

    }

    private void addOneLibrary(Library library) {
        libraryRepository.save(library);
    }


}


