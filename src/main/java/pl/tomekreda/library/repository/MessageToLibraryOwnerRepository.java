package pl.tomekreda.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;

import java.util.UUID;

@RepositoryRestResource
public interface MessageToLibraryOwnerRepository extends JpaRepository<MessageToLibraryOwner, UUID> {

}
