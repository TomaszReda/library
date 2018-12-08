package pl.tomekreda.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, UUID> {


    List<Book> findAllByLibraryAndTitleIsContainingAndBookStateIsIn(Library library, String word, BookState[] array);

    List<Book> findAllByLibraryAndBookStateIsIn(Library library, BookState[] array);

    List<Book> findAllByBookStateIs(BookState bookState);

    List<Book> findAllByBookSearchIsContaining(String booksearch);
    List<Book> findAllByBookSearchIsLike(String booksearch);
    List<Book> findAllByBookSearchContains(String booksearch);
    List<Book> findAllByTitleIsContaining(String word);

}


