package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.request.NotificationRequest;
import pl.tomekreda.library.service.MessageToCasualUserService;

import java.util.UUID;

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

    @PostMapping("/read/notification")
    public ResponseEntity readNotification(@RequestBody NotificationRequest notificationRequest) {
        return messageService.readNotification(notificationRequest.getMessageId());
    }
}
