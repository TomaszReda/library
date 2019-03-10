package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import pl.tomekreda.library.model.user.UserRoleEnum;
import pl.tomekreda.library.repository.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageToCasualUserService {


    private final MessageToCasualUserRepository messageToCasualUserRepository;

    private final MessageToLibraryOwnerRepository messageToLibraryOwnerRepository;

    private final MessageRepository messageRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    private final LibraryRepository libraryRepository;


    public ResponseEntity getAllUnreadNotificationForCasualUser(int page, int size) {
        try {
            Pageable pageableRequest = PageRequest.of(page, size);
            User user = userService.findLoggedUser();
            List<MessageToCasualUser> messageToCasualUsers = messageToCasualUserRepository.findAllByUserAndDateReadIsNullOrderByDataCreateDesc(user);
            int max = (size * (page + 1) > messageToCasualUsers.size()) ? messageToCasualUsers.size() : size * (page + 1);
            Page<Map<String, Object>> pageResult = new PageImpl(messageToCasualUsers.subList(size * page, max), pageableRequest, messageToCasualUsers.size());

            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity readNotification(UUID messageId) {
        try {
            Message message = messageRepository.findById(messageId).orElse(null);
            if (message == null) {
                return ResponseEntity.badRequest().build();
            }
            message.setDateRead(LocalDateTime.now());
            messageRepository.save(message);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity getUnreadNotification(String email) {
        try {
            User user = userRepository.findUserByEmail(email);
            return getUnreadNotification(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity getUnreadNotification() {
        try {
            User user = userService.findLoggedUser();
            return getUnreadNotification(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity getUnreadNotification(User user) {
        int size = 0;

        for (int i = 0; i < user.getUserRoles().size(); i++) {

            if (user.getUserRoles().get(i).getUserRole() == UserRoleEnum.CASUAL_USER) {
                size = messageToCasualUserRepository.findAllByUserAndDateReadIsNullOrderByDataCreateDesc(user).size();

            }
            if (user.getUserRoles().get(i).getUserRole() == UserRoleEnum.LIBRARY_OWNER) {
                List<MessageToLibraryOwner> messageToLibraryOwners = new ArrayList<>();
                List<Library> libraryLists = libraryRepository.findAllByUserMenager(user.getUserMenager());
                for (Library library : libraryLists) {
                    List<MessageToLibraryOwner> tmp = messageToLibraryOwnerRepository.findAllByLibraryAndDateReadIsNullOrderByDataCreateDesc(library);
                    messageToLibraryOwners.addAll(tmp);
                }

                size = messageToLibraryOwners.size();
            }
        }

        return ResponseEntity.ok(size);

    }

    public ResponseEntity readAllNotification() {
        try {
            User user = userService.findLoggedUser();

            for (int i = 0; i < user.getUserRoles().size(); i++) {

                if (user.getUserRoles().get(i).getUserRole() == UserRoleEnum.CASUAL_USER) {
                    List<MessageToCasualUser> messageToCasualUsers = messageToCasualUserRepository.findAllByUserAndDateReadIsNullOrderByDataCreateDesc(user);
                    for (MessageToCasualUser message : messageToCasualUsers) {
                        message.setDateRead(LocalDateTime.now());
                        messageToCasualUserRepository.save(message);
                    }
                    log.info("[Read all notification for Casual User]");

                }
                if (user.getUserRoles().get(i).getUserRole() == UserRoleEnum.LIBRARY_OWNER) {
                    List<Library> libraryList = libraryRepository.findAllByUserMenager(user.getUserMenager());
                    List<MessageToLibraryOwner> messageToLibraryOwners = new ArrayList<>();
                    for (Library library : libraryList) {
                        List<MessageToLibraryOwner> tmp = messageToLibraryOwnerRepository.findAllByLibraryAndDateReadIsNullOrderByDataCreateDesc(library);
                        messageToLibraryOwners.addAll(tmp);
                    }
                    for (MessageToLibraryOwner message : messageToLibraryOwners) {
                        message.setDateRead(LocalDateTime.now());
                        messageToLibraryOwnerRepository.save(message);
                    }
                    log.info("[Read all notification for Library Owner]");

                }
            }
            return ResponseEntity.ok().build();


        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
