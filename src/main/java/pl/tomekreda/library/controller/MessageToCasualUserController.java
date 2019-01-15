package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.service.MessageToCasualUserService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageToCasualUserController {

    private final MessageToCasualUserService messageService;

    @GetMapping("/notification/for/casual/user")
    public ResponseEntity getAllUnreadNotificationForCasualUser(@RequestParam int page, @RequestParam int size) {
        return messageService.getAllUnreadNotificationForCasualUser(page,size);
    }


    @PostMapping("/read/notification")
    public ResponseEntity readNotification(@RequestBody UUID messageId) {
        return messageService.readNotification(messageId);
    }
}
