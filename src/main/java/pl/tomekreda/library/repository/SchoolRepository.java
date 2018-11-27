package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomekreda.library.model.School;

import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, UUID> {
}
