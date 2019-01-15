package pl.tomekreda.library.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.UserMenager;

import java.util.List;
import java.util.UUID;

@RepositoryRestResource
public interface LibraryRepository extends JpaRepository<Library, UUID> {
    Page<Library> findAllByNameContains(String name, Pageable pageable);
    List<Library> findAllByUserMenager(UserMenager userMenager);
}
