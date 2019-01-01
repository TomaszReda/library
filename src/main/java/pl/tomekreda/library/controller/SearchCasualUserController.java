package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.tomekreda.library.service.SearchCasualUserService;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchCasualUserController {

    private final SearchCasualUserService searchCasualUserService;
    @GetMapping("/search/book")
    public ResponseEntity search( @RequestParam(defaultValue = "") String word, @RequestParam int page, @RequestParam int size) {
        if (word.length() < 3 || word.equals(null))
            return searchCasualUserService.searchAll( page, size);
        else
            return searchCasualUserService.search( word, page, size);

    }

    @PreAuthorize("hasAuthority('ROLE_CASUAL_USER')")
    @GetMapping("/search/reserv/book")
    public ResponseEntity getReservBook(@RequestParam int page, @RequestParam int size,@RequestParam(defaultValue = "") String word){
            return searchCasualUserService.getReservBook(word,page,size);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/search/user/{email}")
    public ResponseEntity getSearchUser(@PathVariable String email){
        return searchCasualUserService.searchByEmail(email);
    }

    @PreAuthorize("hasAuthority('ROLE_CASUAL_USER')")
    @GetMapping("/get/booked/book")
    public ResponseEntity bookedBook(@RequestParam int page,@RequestParam int size) {
        return searchCasualUserService.bookedBook(page,size);
    }

}
