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
import pl.tomekreda.library.model.library.Library;
import pl.tomekreda.library.model.user.User;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.repository.LibraryRepository;
import pl.tomekreda.library.repository.UserRepository;
import pl.tomekreda.library.utils.Utils;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@Transactional
public class SearchLibraryOwnerService {

    private final LibraryRepository libraryRepository;

    private final BookRepository bookRepository;

    private final UserService userService;

    private final UserRepository userRepository;

    public ResponseEntity search(UUID libraryId, String word, int page, int size) {
        try {
            Library library = libraryRepository.findById(libraryId).orElse(null);
            if (!userService.findLoggedUser().getUserMenager().equals(library.getUserMenager())) {
                return ResponseEntity.badRequest().build();
            }
            log.info("[Search word]=" + word);

            BookState[] array = new BookState[3];
            array[1] = BookState.NOTRESERVED;
            array[0] = BookState.BOOKED;
            array[2] = BookState.CONFIRMED;
            List<Book> tmpBookList = bookRepository.findAllByLibraryAndTitleIsContainingAndBookStateIsIn(library, word, array);
            Utils utils=new Utils();
            List<Map<String, Object>> bookLists=utils.createBookList(tmpBookList);
            Pageable pageable = new PageRequest(page, size);
            int max = (size * (page + 1) > bookLists.size()) ? bookLists.size() : size * (page + 1);
            Page<List<Map<String, Object>>> bookListPageResult = new PageImpl(bookLists.subList(size * page, max), pageable, bookLists.size());
            log.info("[Get Book list]=" + bookLists);

//            log.info("[Search response]=");
            return ResponseEntity.ok(bookListPageResult);
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
            Utils utils=new Utils();
            List<Map<String, Object>> bookList=utils.createBookList(tmpbookList);
            int max = (size * (page + 1) > bookList.size()) ? bookList.size() : size * (page + 1);
            Pageable pageable = new PageRequest(page, size);

            log.info("[Get Book list]=" + bookList);
            Page<List<Map<String, Object>>> pageResult = new PageImpl(bookList.subList(size * page, max), pageable, bookList.size());

            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity searchReservBook(UUID userId,int size,int page) {
        try {
            User user = userRepository.findById(userId).orElse(null);
            List<Book> booktmpList = bookRepository.findAllByUserCasualAndBookState(user.getUserCasual(), BookState.BOOKED);
            List<Map<String, Object>> bookList=createBookList(booktmpList);

            int max = (size * (page + 1) > bookList.size()) ? bookList.size() : size * (page + 1);
            Pageable pageable = new PageRequest(page, size);

            log.info("[Get Book list]=" + bookList);
            Page<List<Map<String, Object>>> pageResult = new PageImpl(bookList.subList(size * page, max), pageable, bookList.size());
            return ResponseEntity.ok(pageResult);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().build();
        }
    }


    private List<Map<String, Object>> createBookList(List<Book> bookList) throws NoSuchFieldException {
        List<Map<String, Object>> mapArrayList=new ArrayList<>();
        for(Book b: bookList){
            Map<String,Object> tmp=new HashMap<>();
            tmp.put(Book.class.getDeclaredField("author").getName(),b.getAuthor());
            tmp.put(Book.class.getDeclaredField("title").getName(),b.getTitle());
            tmp.put(Book.class.getDeclaredField("publisher").getName(),b.getPublisher());
            tmp.put(Book.class.getDeclaredField("date").getName(),b.getDate());
            tmp.put(Book.class.getDeclaredField("quant").getName(),b.getQuant());
            tmp.put(Book.class.getDeclaredField("ISBN").getName(),b.getISBN());
            tmp.put("bookId",b.getID());
            tmp.put("libraryName",b.getLibrary().getName());
            mapArrayList.add(tmp);
        }
        return  mapArrayList;
    }



}

