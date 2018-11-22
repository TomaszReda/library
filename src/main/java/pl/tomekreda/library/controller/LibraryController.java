package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.request.AddLibraryRequest;
import pl.tomekreda.library.service.LibraryService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LibraryController {



    private final LibraryService libraryService;


    @PostMapping("/add/library")
    public ResponseEntity addLibrary(@RequestBody AddLibraryRequest addLibraryRequest) {
        System.out.println(addLibraryRequest);
        return libraryService.addLibrary(addLibraryRequest);
    }

}
