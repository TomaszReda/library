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
import pl.tomekreda.library.request.AddLibraryRequest;
import pl.tomekreda.library.request.UpdateLibraryRequest;

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


    public ResponseEntity updateLibrary(UpdateLibraryRequest updateLibraryRequest) {
        try {
            if (updateLibraryRequest.getLongitude().equals("21.0158") && updateLibraryRequest.getLatitude().equals("52.2051")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Najpierw zaznacz na mapie lokalizacje ");
            }


            log.info("[Update library request]=" + updateLibraryRequest);
            Library library = libraryRepository.findById(updateLibraryRequest.getLibraryID()).orElse(null);
            if(library.getUserMenager().equals(userService.findLoggedUser().getUserMenager())){
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
            ex.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

    }


    public ResponseEntity addLibrary(AddLibraryRequest addLibraryRequest) {
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
            ex.printStackTrace();
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
            map.put("libraryId", library.getID());
            map.put("libraryName", library.getName());
            listLibrary.add(map);
        }
        return listLibrary;
    }


    public ResponseEntity getLibraryById(UUID libraryID) {
        try {
            Library library = libraryRepository.findById(libraryID).orElse(null);
            if(!library.getUserMenager().equals(userService.findLoggedUser().getUserMenager())){
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


    private Library CreateLibrary(AddLibraryRequest addLibraryRequest, User user) {
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


    private Library CreateLibrary(UpdateLibraryRequest updateLibraryRequest, Library library, User user) {
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
