package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
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

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Transactional
public class LibraryService {

    private final LibraryRepository libraryRepository;

    private final UserRepository userRepository;

    private final UserService userService;

    public ResponseEntity addLibrary(AddLibraryRequest addLibraryRequest) {
        try {

            if (addLibraryRequest.getLongitude().equals("21.0158") && addLibraryRequest.getLatitude().equals("52.2051")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Najpierw zaznacz na mapie lokalizacje ");
            }

            User user = userService.findLoggedUser();
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
}
