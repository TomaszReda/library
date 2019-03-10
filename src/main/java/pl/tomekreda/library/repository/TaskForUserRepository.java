package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.task.TaskForUser;
import pl.tomekreda.library.model.task.TaskStatus;

import java.util.UUID;

@RepositoryRestResource
public interface TaskForUserRepository extends JpaRepository<TaskForUser, UUID> {

    TaskForUser findByBookAndTaskStatus(Book book, TaskStatus taskStatus);
}
