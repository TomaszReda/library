package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.tomekreda.library.model.Person;

import java.util.UUID;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
}
