package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.Book;
import pl.tomekreda.library.model.BookState;
import pl.tomekreda.library.model.Library;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, UUID> {


    List<Book> findAllByLibraryAndTitleIsContainingAndBookStateIsIn(Library library, String word, BookState[] array);

    List<Book> findAllByLibraryAndBookStateIsIn(Library library, BookState[] array);
}


