package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddLibraryRequest;

import java.io.IOException;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private final UserRepository userRepository;

    public ResponseEntity addLibrary(AddLibraryRequest addLibraryRequest) {
        try {

            if (addLibraryRequest.getLongitude().equals("21.0158") && addLibraryRequest.getLatitude().equals("52.2051")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Najpierw zaznacz na mapie lokalizacje ");
            }

            User user = userRepository.findById(addLibraryRequest.getUserID()).orElse(null);
            Library tmp = new Library();
            tmp.setOwner(user);
            tmp.setCity(addLibraryRequest.getCity());
            tmp.setEmail(addLibraryRequest.getEmail());
            tmp.setLatitude(addLibraryRequest.getLatitude());
            if (addLibraryRequest.getLocal() != null)
                tmp.setLocal(addLibraryRequest.getLocal());
            tmp.setLongitude(addLibraryRequest.getLongitude());
            tmp.setName(addLibraryRequest.getName());
            tmp.setNumber(addLibraryRequest.getNumber());
            tmp.setPostalCode(addLibraryRequest.getPostalCode());
            if (addLibraryRequest.getStreet() != null)
                tmp.setStreet(addLibraryRequest.getStreet());
            libraryRepository.save(tmp);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
