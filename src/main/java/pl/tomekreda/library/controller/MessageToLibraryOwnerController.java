package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tomekreda.library.service.MessageToLibraryOwnerService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MessageToLibraryOwnerController {

    private final MessageToLibraryOwnerService messageToLibraryOwnerService;


    @GetMapping("/notification/for/library/owner")
    public ResponseEntity getAllUnreadNotificationForLibraryOwner(@RequestParam int page, @RequestParam int size) {
        return messageToLibraryOwnerService.getAllUnreadNotificationForLibraryOwner(page, size);
    }
}
