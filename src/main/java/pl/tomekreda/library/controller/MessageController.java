package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.service.MessageService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageController {

    private final MessageService messageService;

    @GetMapping("/notification/for/casual/user")
    public ResponseEntity getAllUnreadNotificationForCasualUser(@RequestParam int page, @RequestParam int size) {
        return messageService.getAllUnreadNotificationForCasualUser(page,size);
    }

    @GetMapping("/notification/for/library/owner")
    public ResponseEntity getAllUnreadNotificationForLibraryOwner(@RequestParam int page, @RequestParam int size) {
        return messageService.getAllUnreadNotificationForLibraryOwner(page,size);
    }

    @PostMapping("/read/notification")
    public ResponseEntity readNotification(@RequestBody UUID messageId) {
        return messageService.readNotification(messageId);
    }
}
