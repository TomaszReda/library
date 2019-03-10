package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.request.NotificationRequest;
import pl.tomekreda.library.service.MessageToCasualUserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageToCasualUserController {

    private final MessageToCasualUserService messageService;

    @PreAuthorize("hasAuthority('ROLE_CASUAL_USER')")
    @GetMapping("/notification/for/casual/user")
    public ResponseEntity getAllUnreadNotificationForCasualUser(@RequestParam int page, @RequestParam int size) {
        return messageService.getAllUnreadNotificationForCasualUser(page,size);
    }

    @PreAuthorize("hasAnyAuthority('ROLE_CASUAL_USER','ROLE_LIBRARY_OWNER')")
    @PostMapping("/read/notification")
    public ResponseEntity readNotification(@RequestBody NotificationRequest notificationRequest) {
        return messageService.readNotification(notificationRequest.getMessageId());
    }

    @PostMapping("/get/unread/notification")
    public ResponseEntity getUnreadNotification(@RequestBody String email) {
        return messageService.getUnreadNotification(email);
    }

    @GetMapping("/get/unread/notification")
    public ResponseEntity getUnreadNotification() {
        return messageService.getUnreadNotification();
    }


    @GetMapping("/read/all/notification")
    public ResponseEntity readAllNotification(){
        return messageService.readAllNotification();
    }
}
