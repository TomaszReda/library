package pl.tomekreda.library.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.repository.BookRepository;
import pl.tomekreda.library.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SearchCasualUserService {

    private final BookRepository bookRepository;

    public ResponseEntity searchAll(int page,int size){
        try{
            List<Book> bookList=bookRepository.findAllByBookStateIs(BookState.NOTRESERVED);

            Utils utils=new Utils();
            List<Map<String, Object>> bookListss=utils.createBookList(bookList);
            int max = (size * (page + 1) > bookListss.size()) ? bookListss.size() : size * (page + 1);
            Pageable pageableRequest = new PageRequest(page, size);
            Page<List<Map<String, Object>>> bookSearchList = new PageImpl(bookListss.subList(size * page, max), pageableRequest, bookListss.size());

            return ResponseEntity.ok(bookSearchList);
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

    public ResponseEntity search(String word,int page,int size){
        try{
            System.err.println("Book search "+word+" result: ");

            System.err.println(" ");


            System.err.println(" findAllByBookSearchIsContaining(word) "+bookRepository.findAllByBookSearchIsContaining(word));
            System.err.println(" findAllByBookSearchIsLike(word) "+bookRepository.findAllByBookSearchIsLike(word));
            System.err.println(" findAllByBookSearchContains(word)"+bookRepository.findAllByBookSearchContains(word));
            System.err.println(" findAllByTitleIsContaining(String word)"+bookRepository.findAllByTitleIsContaining(word));

            return ResponseEntity.ok().build();
        }
        catch(Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }




}
