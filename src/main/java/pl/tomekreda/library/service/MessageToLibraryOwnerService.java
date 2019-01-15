package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.MessageToLibraryOwnerRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageToLibraryOwnerService {
    private final MessageToLibraryOwnerRepository messageToLibraryOwnerRepository;

    private final LibraryRepository libraryRepository;

    private final UserService userService;


    public ResponseEntity getAllUnreadNotificationForLibraryOwner(int page, int size) {
        try {
            User user = userService.findLoggedUser();
            List<Library> libraryList = libraryRepository.findAllByUserMenager(user.getUserMenager());
            List<MessageToLibraryOwner> messageToLibraryOwners = new ArrayList<>();
            for (Library library : libraryList) {
                List<MessageToLibraryOwner> tmp = messageToLibraryOwnerRepository.findAllByLibraryAndDateReadIsNullOrderByDataCreateDesc(library);
                messageToLibraryOwners.addAll(tmp);
            }
            Pageable pageable = new PageRequest(page, size);
            int max = (size * (page + 1) > messageToLibraryOwners.size()) ? messageToLibraryOwners.size() : size * (page + 1);
            Page<Map<String, Object>> pageResult = new PageImpl(messageToLibraryOwners.subList(size * page, max), pageable, messageToLibraryOwners.size());


            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
