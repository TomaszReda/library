package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.Book;
import pl.tomekreda.library.model.BookState;
import pl.tomekreda.library.model.Library;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.utils.Utils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class SearchService {

    private final LibraryRepository libraryRepository;

    private final BookRepository bookRepository;

    private final UserService userService;


    public ResponseEntity search(UUID libraryId, String word, int page, int size) {
        try {

            log.info("[Search word]=" + word);
            Library library = libraryRepository.findById(libraryId).orElse(null);
            if (!userService.findLoggedUser().getUserMenager().equals(library.getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            BookState[] arr = new BookState[3];
            arr[0] = BookState.BOOKED;
            arr[1] = BookState.NOTRESERVED;
            arr[2] = BookState.CONFIRMED;
            List<Book> tmpbookList = bookRepository.findAllByLibraryAndTitleIsContainingAndBookStateIsIn(library, word, arr);

            List<Map<String, Object>> bookList = createBook(tmpbookList);
            Pageable pageable = new PageRequest(page, size);
            int max = (size * (page + 1) > bookList.size()) ? bookList.size() : size * (page + 1);
            log.info("[Get Book list]=" + bookList);
            Page<List<Map<String, Object>>> pageResult = new PageImpl(bookList.subList(size * page, max), pageable, bookList.size());

//            log.info("[Search response]=");
            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity searchAll(UUID libraryId, int page, int size) {
        try {

            Library library = libraryRepository.findById(libraryId).orElse(null);
            if (!userService.findLoggedUser().getUserMenager().equals(library.getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            BookState[] arr = new BookState[3];
            arr[0] = BookState.BOOKED;
            arr[1] = BookState.NOTRESERVED;
            arr[2] = BookState.CONFIRMED;
            List<Book> tmpbookList = bookRepository.findAllByLibraryAndBookStateIsIn(library, arr);
            List<Map<String, Object>> bookList = createBook(tmpbookList);
            Pageable pageable = new PageRequest(page, size);
            int max = (size * (page + 1) > bookList.size()) ? bookList.size() : size * (page + 1);
            log.info("[Get Book list]=" + bookList);
            Page<List<Map<String, Object>>> pageResult = new PageImpl(bookList.subList(size * page, max), pageable, bookList.size());

            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    private List<Map<String, Object>> createBook(List<Book> bookList) {
        List<Map<String, Object>> listLibrary = new ArrayList<>();
        Utils utils=new Utils();
        for (Book b : bookList) {
            Map<String, Object> map = new HashMap<>();
            map.put("author", b.getAuthor());
            map.put("title", b.getTitle());
            map.put("publisher", b.getPublisher());
            map.put("libraryId", b.getLibrary().getID());
            map.put("bookState", utils.convert(b.getBookState()));
            map.put("quant", b.getQuant());
            map.put("bookId", b.getID());
            listLibrary.add(map);
        }
        return listLibrary;
    }



}

