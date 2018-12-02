package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.request.AddLibraryRequest;
import pl.tomekreda.library.request.UpdateLibraryRequest;
import pl.tomekreda.library.service.LibraryService;

import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryController {


    private final LibraryService libraryService;

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @PostMapping("/add/library")
    public ResponseEntity addLibrary(@RequestBody AddLibraryRequest addLibraryRequest) {
        return libraryService.addLibrary(addLibraryRequest);
    }

    @PreAuthorize("hasAuthority('ROLE_LIBRARY_OWNER')")
    @PutMapping("update/library")
    public ResponseEntity updateLibrary(@RequestBody UpdateLibraryRequest updateLibraryRequest) {
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


}
