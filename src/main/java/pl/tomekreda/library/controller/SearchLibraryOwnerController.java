package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.service.SearchLibraryOwnerService;

import java.util.UUID;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchLibraryOwnerController {

    private final SearchLibraryOwnerService searchService;


    @GetMapping("/search/library/{libraryId}/book")
    public ResponseEntity search(@PathVariable UUID libraryId, @RequestParam(defaultValue = "") String word, @RequestParam int page, @RequestParam int size) {
        if (word.length() < 3 || word.equals(null))
            return searchService.searchAll(libraryId, page, size);
        else
            return searchService.search(libraryId, word, page, size);
    }


    @GetMapping("/search/user/{userId}/reserv/book")
    public ResponseEntity searchReservBook(@PathVariable UUID userId, @RequestParam int page, @RequestParam int size) {
        return searchService.searchReservBook(userId,size,page);
    }

    @GetMapping("/search/user/{userId}/booked/book")
    public ResponseEntity searchBookedBook(@PathVariable UUID userId, @RequestParam int page, @RequestParam int size) {
        return searchService.searchBookedBook(userId,size,page);
    }

}
