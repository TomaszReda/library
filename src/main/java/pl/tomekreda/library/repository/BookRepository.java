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


    Page<List<Book>> findAllByBookStateAndLibraryAndTitleIsContaining(BookState state, Library library, String word, Pageable pageable);
    Page<List<Book>> findAllByBookStateAndLibrary(BookState bookState,Library library, Pageable pageable);
}
