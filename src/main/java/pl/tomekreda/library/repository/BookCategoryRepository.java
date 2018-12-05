package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.BookCategory;

@RepositoryRestResource
public interface BookCategoryRepository extends JpaRepository<BookCategory, Long> {

    BookCategory findFirstByCategoryType(String categoryType);
}
