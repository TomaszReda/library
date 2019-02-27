package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddUpdateLibraryRequest;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
@Slf4j
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private final UserRepository userRepository;

    private final UserService userService;


    public ResponseEntity updateLibrary(AddUpdateLibraryRequest updateLibraryRequest) {
        try {
            if (updateLibraryRequest.getLongitude().equals("21.0158") && updateLibraryRequest.getLatitude().equals("52.2051")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Najpierw zaznacz na mapie lokalizacje ");
            }


            log.info("[Update library request]=" + updateLibraryRequest);
            Library library = libraryRepository.findById(updateLibraryRequest.getLibraryID()).orElse(null);
            if (library == null) {
                return ResponseEntity.badRequest().build();
            }
            if (library.getUserMenager().equals(userService.findLoggedUser().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            User user = userService.findLoggedUser();

            if (!user.getUserMenager().equals(library.getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            library = CreateLibrary(updateLibraryRequest, library, user);

            log.info("[Updated library]=" + library);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }

    }


    public ResponseEntity addLibrary(AddUpdateLibraryRequest addLibraryRequest) {
        try {

            if (addLibraryRequest.getLongitude().equals("21.0158") && addLibraryRequest.getLatitude().equals("52.2051")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Najpierw zaznacz na mapie lokalizacje ");
            }

            log.info("[Add library request]=" + addLibraryRequest);
            User user = userService.findLoggedUser();

            Library tmp = CreateLibrary(addLibraryRequest, user);

            log.info("[Added library]=" + tmp);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity getLibraryList(int page, int size) {
        try {
            List<Library> libraryList = userService.findLoggedUser().getUserMenager().getLibraryList();
            List<Map<String, Object>> libraryMap = createLibraryMap(libraryList);
            Pageable pageable = new PageRequest(page, size);
            int max = (size * (page + 1) > libraryMap.size()) ? libraryMap.size() : size * (page + 1);
            log.info("[Get library list]=" + libraryMap);

            Page<List<Map<String, Object>>> pageResult = new PageImpl(libraryMap.subList(size * page, max), pageable, libraryList.size());

            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    private List<Map<String, Object>> createLibraryMap(List<Library> libraryList) {
        List<Map<String, Object>> listLibrary = new ArrayList<>();
        for (Library library : libraryList) {
            Map<String, Object> map = new HashMap<>();
            map.put("libraryId", library.getId());
            map.put("libraryName", library.getName());
            listLibrary.add(map);
        }
        return listLibrary;
    }


    public ResponseEntity getLibraryById(UUID libraryID) {
        try {
            Library library = libraryRepository.findById(libraryID).orElse(null);
            if (library == null) {
                return ResponseEntity.badRequest().build();
            }
            if (!library.getUserMenager().equals(userService.findLoggedUser().getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            if (!userService.findLoggedUser().getUserMenager().equals(library.getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }

            return ResponseEntity.ok(library);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity getLibraryDetails(UUID libraryID) {
        try {
            Library tmp = libraryRepository.findById(libraryID).orElse(null);

            Map<String, Object> library = libraryDetailsForAdmin(tmp);
            return ResponseEntity.ok(library);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    private Map<String, Object> libraryDetailsForAdmin(Library tmp) throws NoSuchFieldException {
        Map<String, Object> library = new HashMap<>();
        library.put(Library.class.getDeclaredField("name").getName(), tmp.getName());
        library.put(Library.class.getDeclaredField("email").getName(), tmp.getEmail());
        library.put(Library.class.getDeclaredField("city").getName(), tmp.getCity());
        library.put(Library.class.getDeclaredField("latitude").getName(), tmp.getLatitude());
        library.put(Library.class.getDeclaredField("local").getName(), tmp.getLocal());
        library.put(Library.class.getDeclaredField("longitude").getName(), tmp.getLongitude());
        library.put(Library.class.getDeclaredField("number").getName(), tmp.getNumber());
        library.put(Library.class.getDeclaredField("postalCode").getName(), tmp.getPostalCode());
        library.put(Library.class.getDeclaredField("street").getName(), tmp.getStreet());
        library.put(Library.class.getDeclaredField("id").getName(), tmp.getId());

        User user = userRepository.findAllByUserMenager(tmp.getUserMenager());
        Map<String, Object> owner = new HashMap<>();
        owner.put(User.class.getDeclaredField("email").getName(), user.getEmail());
        owner.put(User.class.getDeclaredField("lastname").getName(), user.getLastname());
        owner.put(User.class.getDeclaredField("firstname").getName(), user.getFirstname());
        owner.put(User.class.getDeclaredField("phoneNumber").getName(), user.getPhoneNumber());
        library.put("owner", owner);

        return library;
    }


    private Library CreateLibrary(AddUpdateLibraryRequest addLibraryRequest, User user) {
        Library tmp = new Library();
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
        tmp.setUserMenager(user.getUserMenager());
        tmp = libraryRepository.save(tmp);
        return tmp;
    }


    private Library CreateLibrary(AddUpdateLibraryRequest updateLibraryRequest, Library library, User user) {
        library.setCity(updateLibraryRequest.getCity());
        library.setLatitude(updateLibraryRequest.getLatitude());
        library.setEmail(updateLibraryRequest.getEmail());
        if (updateLibraryRequest.getLocal() != null)
            library.setLocal(updateLibraryRequest.getLocal());
        library.setName(updateLibraryRequest.getName());
        library.setLongitude(updateLibraryRequest.getLongitude());
        library.setNumber(updateLibraryRequest.getNumber());
        library.setPostalCode(updateLibraryRequest.getPostalCode());
        library.setUserMenager(user.getUserMenager());
        if (updateLibraryRequest.getStreet() != null)
            library.setStreet(updateLibraryRequest.getStreet());
        library = libraryRepository.save(library);
        return library;
    }

    public ResponseEntity getAllLibrary(int page, int size) {
        try {
            Pageable pageableRequest = new PageRequest(page, size);
            Page<Library> libraryList = libraryRepository.findAll(pageableRequest);
            return ResponseEntity.ok(libraryList);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
