package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.UserCasual;
import pl.tomekreda.library.model.user.UserMenager;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, UUID> {


    List<Book> findAllByLibraryAndTitleIsContainingAndBookStateIsIn(Library library, String word, BookState[] array);

    List<Book> findAllByLibraryAndBookStateIsIn(Library library, BookState[] array);

    List<Book> findAllByBookStateIs(BookState bookState);

    List<Book> findAllByBookSearchContainsAndBookState(String booksearch,BookState bookState);

    List<Book> findAllByUserCasualAndBookStateAndBookSearchContains(UserCasual userCasual,BookState bookState,String booksearch);

    List<Book> findAllByUserCasualAndBookStateAndUserMenager(UserCasual userCasual, BookState bookState, UserMenager userMenager);

    List<Book> findAllByUserCasualAndBookState(UserCasual userCasual, BookState bookState);

    Book findFirstByAuthorAndTitleAndPublisherAndDateAndLibraryAndBookState(String author, String titile, String publisher, LocalDate date,Library library,BookState bookState);


}


