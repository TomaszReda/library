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
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.utils.Utils;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchCasualUserService {

    private final BookRepository bookRepository;

    private final UserService userService;

    private final BookService bookService;

    private Utils utils = new Utils();

    public ResponseEntity searchAll(int page, int size) {
        try {
            List<Book> bookList = bookRepository.findAllByBookStateIs(BookState.NOTRESERVED);

            List<Map<String, Object>> bookListss = utils.createBookList(bookList);
            int max = (size * (page + 1) > bookListss.size()) ? bookListss.size() : size * (page + 1);
            Pageable pageableRequest = new PageRequest(page, size);
            Page<List<Map<String, Object>>> bookSearchList = new PageImpl(bookListss.subList(size * page, max), pageableRequest, bookListss.size());
            log.info("[Search result]=" + bookSearchList);

            return ResponseEntity.ok(bookSearchList);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity search(String word, int page, int size) {
        try {
            List<Book> bookList = bookRepository.findAllByBookSearchContainsAndBookState(word, BookState.NOTRESERVED);
            List<Map<String, Object>> bookLists = utils.createBookList(bookList);
            int max = (size * (page + 1) > bookList.size()) ? bookList.size() : size * (page + 1);
            Pageable pageableRequest = new PageRequest(page, size);
            Page<List<Map<String, Object>>> bookSearchList = new PageImpl(bookLists.subList(size * page, max), pageableRequest, bookLists.size());
            log.info("[Search result]=" + bookSearchList);
            return ResponseEntity.ok(bookSearchList);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    public ResponseEntity getReservBook(String word, int page, int size) {
        try {
            User user = userService.findLoggedUser();
            List<Book> bookList = new ArrayList<>();
            if (word.length() < 3 || word.equals(null)) {
                bookList = bookRepository.findAllByUserCasualAndBookState(user.getUserCasual(), BookState.BOOKED);
            } else {
                bookList = bookRepository.findAllByUserCasualAndBookStateAndBookSearchContains(user.getUserCasual(), BookState.BOOKED, word);

            }
            List<Map<String, Object>> bookLists = utils.createBookList(bookList);
            int max = (size * (page + 1) > bookList.size()) ? bookList.size() : size * (page + 1);
            Pageable pageableRequest = new PageRequest(page, size);
            Page<List<Map<String, Object>>> bookSearchList = new PageImpl(bookLists.subList(size * page, max), pageableRequest, bookLists.size());
            log.info("[Search result]=" + bookSearchList);
            return ResponseEntity.ok(bookSearchList);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


}
