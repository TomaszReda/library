package pl.tomekreda.library.utils;

import lombok.extern.slf4j.Slf4j;
import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public  final class Utils {

    private Utils() {
        //not caled
    }


    public  static String convert(BookState bookState) {
        String state = "";
        if (bookState.equals(BookState.BOOKED)) {
            state = "Zarezerwowana";
        } else if (bookState.equals(BookState.CONFIRMED)) {
            state = "Wyporzyczona";
        } else if (bookState.equals(BookState.NOTRESERVED)) {
            state = "Dostępna";
        } else {
            state = "Usunieta";
        }
        return state;
    }

    public static List<Map<String, Object>> createBookList(List<Book> bookList) {
        List<Map<String, Object>> listLibrary = new ArrayList<>();
        Utils utils = new Utils();
        for (Book b : bookList) {
            Map<String, Object> map = new HashMap<>();
            map.put("author", b.getAuthor());
            map.put("title", b.getTitle());
            map.put("publisher", b.getPublisher());
            map.put("libraryId", b.getLibrary().getId());
            map.put("bookState", utils.convert(b.getBookState()));
            map.put("quant", b.getQuant());
            map.put("bookId", b.getId());
            listLibrary.add(map);
        }
        return listLibrary;
    }


    public static List<Map<String, Object>> createBookListForUserOwner(List<Book> bookList) throws NoSuchFieldException {
        List<Map<String, Object>> mapArrayList = new ArrayList<>();
        for (Book b : bookList) {
            Map<String, Object> tmp = new HashMap<>();
            tmp.put(Book.class.getDeclaredField("author").getName(), b.getAuthor());
            tmp.put(Book.class.getDeclaredField("title").getName(), b.getTitle());
            tmp.put(Book.class.getDeclaredField("publisher").getName(), b.getPublisher());
            tmp.put(Book.class.getDeclaredField("date").getName(), b.getDate());
            tmp.put(Book.class.getDeclaredField("quant").getName(), b.getQuant());
            tmp.put(Book.class.getDeclaredField("ISBN").getName(), b.getIsbn());
            tmp.put("bookId", b.getId());
            tmp.put("libraryName", b.getLibrary().getName());
            mapArrayList.add(tmp);
        }
        return mapArrayList;
    }

}
