package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import pl.tomekreda.library.model.book.Book;

import java.util.UUID;

@RepositoryRestResource
public interface BookRepository extends JpaRepository<Book, UUID> {
}
