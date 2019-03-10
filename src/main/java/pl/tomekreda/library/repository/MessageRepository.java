package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.tomekreda.library.model.message.Message;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<Message, UUID> {
}
