package pl.tomekreda.library.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.tomekreda.library.request.SearchRequest;
import pl.tomekreda.library.service.SearchService;

import java.util.UUID;

@Controller
@RequestMapping("/api")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchController {

    private SearchService searchService;


    @GetMapping("/search/library/{libraryId}")
    public ResponseEntity search(@RequestBody UUID libraryId,@RequestParam String word ,@RequestParam int page, @RequestParam int size) {
        return searchService.search(libraryId,word, page, size);
    }

}
