package pl.tomekreda.library.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.tomekreda.library.model.book.Book;

@Service
public class BookService {


    public ResponseEntity addBook(Book book) {
        try{

            return ResponseEntity.ok().build();
        }catch (Exception ex){
            return ResponseEntity.badRequest().build();
        }
    }

}
