package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.tomekreda.library.service.SearchAdminService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchAdminController {

    private final SearchAdminService adminService;

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/search/user")
    public ResponseEntity searchUser( @RequestParam(defaultValue = "") String word, @RequestParam int page, @RequestParam int size) {
        if (word.length() < 3 || word.equals(null))
            return adminService.searchAllUser( page, size);
        else
            return adminService.searchUser( word, page, size);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/search/library")
    public ResponseEntity searchLirary( @RequestParam(defaultValue = "") String word, @RequestParam int page, @RequestParam int size) {
        if (word.length() < 3 || word.equals(null))
            return adminService.searchAllLibrary( page, size);
        else
            return adminService.searchLibrary( word, page, size);
    }
}
