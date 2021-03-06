package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.request.AddUpdateLibraryRequest;
import pl.tomekreda.library.service.LibraryService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryController {


    private final LibraryService libraryService;

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @PostMapping("/add/library")
    public ResponseEntity addLibrary(@RequestBody AddUpdateLibraryRequest addLibraryRequest) {
        return libraryService.addLibrary(addLibraryRequest);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @PutMapping("update/library")
    public ResponseEntity updateLibrary(@RequestBody AddUpdateLibraryRequest updateLibraryRequest) {
        return libraryService.updateLibrary(updateLibraryRequest);
    }


    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/get/library/list")
    public ResponseEntity getLibraryList(@RequestParam int page, @RequestParam int size) {
        return libraryService.getLibraryList(page, size);

    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @GetMapping("/get/library/{libraryID}")
    public ResponseEntity getLibraryById(@PathVariable UUID libraryID) {
        return libraryService.getLibraryById(libraryID);

    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/library/get/all")
    public ResponseEntity getAllLibrary(@RequestParam int page, @RequestParam int size) {
        return libraryService.getAllLibrary(page, size);
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/library/{libraryID}/details")
    public ResponseEntity getLibraryDetails(@PathVariable UUID libraryID) {
        return libraryService.getLibraryDetails(libraryID);

    }
}
