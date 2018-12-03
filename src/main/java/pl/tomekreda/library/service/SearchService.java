package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.request.SearchRequest;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchService {

    private final LibraryRepository libraryRepository;

    private final BookRepository bookRepository;

    private final UserService userService;


    public ResponseEntity search(UUID libraryId,String word, int page, int size) {
        try {

            Library library = libraryRepository.findById(libraryId).orElse(null);
            if (!userService.findLoggedUser().getUserMenager().equals(library.getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }

            Pageable pageable = new PageRequest(page, size);
            Page<List<Book>> pageResult =bookRepository.findAllByBookStateAndLibraryAndTitleIsContaining(BookState.NOTRESERVED, library, word,pageable);

            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }
}
