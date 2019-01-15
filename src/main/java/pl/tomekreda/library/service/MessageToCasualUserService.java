package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.message.Message;
import pl.tomekreda.library.model.message.MessageToCasualUser;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.MessageRepository;
import pl.tomekreda.library.repository.MessageToCasualUserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageToCasualUserService {


    private final MessageToCasualUserRepository messageToCasualUserRepository;

    private final MessageRepository messageRepository;

    private final UserService userService;


    public ResponseEntity getAllUnreadNotificationForCasualUser(int page, int size) {
        try {
            Pageable pageableRequest = new PageRequest(page, size);
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
            Message message=messageRepository.findById(messageId).orElse(null);
            message.setDataCreate(LocalDateTime.now());
            messageRepository.save(message);
            return ResponseEntity.ok().build();
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
