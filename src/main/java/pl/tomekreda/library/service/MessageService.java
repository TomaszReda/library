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
import pl.tomekreda.library.model.message.Message;
import pl.tomekreda.library.model.message.MessageToCasualUser;
import pl.tomekreda.library.model.message.MessageToLibraryOwner;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.MessageRepository;
import pl.tomekreda.library.repository.MessageToCasualUserRepository;
import pl.tomekreda.library.repository.MessageToLibraryOwnerRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageService {

    private final MessageToLibraryOwnerRepository messageToLibraryOwnerRepository;

    private final MessageToCasualUserRepository messageToCasualUserRepository;

    private final MessageRepository messageRepository;

    private final UserService userService;

    private final LibraryRepository libraryRepository;

    public ResponseEntity getAllUnreadNotificationForCasualUser(int page, int size) {
        try {
            System.err.println("cos");
            Pageable pageableRequest = new PageRequest(page, size);
            User user = userService.findLoggedUser();
            Page<MessageToCasualUser> messageToCasualUsers = messageToCasualUserRepository.findAllByUserAndAndDateReadIsNull(user, pageableRequest);
            return ResponseEntity.ok(messageToCasualUsers);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity getAllUnreadNotificationForLibraryOwner(int page, int size) {
        try {
            User user = userService.findLoggedUser();
            List<Library> libraryList = libraryRepository.findAllByUserMenager(user.getUserMenager());
            List<MessageToLibraryOwner> messageToLibraryOwners = new ArrayList<>();
            for (Library library : libraryList) {
                List<MessageToLibraryOwner> tmp = messageToLibraryOwnerRepository.findAllByLibrary(library);
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

    public ResponseEntity readNotification(UUID messageId) {
        try {
            Message message=messageRepository.findById(messageId).orElse(null);
            message.setDataCreate(LocalDateTime.now());
            messageRepository.save(message);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
