package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import pl.tomekreda.library.request.SearchRequest;
import pl.tomekreda.library.service.SearchService;

import java.util.UUID;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    private final SearchService searchService;


    @GetMapping("/search/library/{libraryId}")
    public ResponseEntity search(@PathVariable UUID libraryId, @RequestParam(defaultValue = "") String word, @RequestParam int page, @RequestParam int size) {
            if (word.length() < 3 || word.equals(null))
            return searchService.searchAll(libraryId, page, size);
        else
            return searchService.search(libraryId, word, page, size);

    }

}