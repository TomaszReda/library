package pl.tomekreda.library.test.helpers.bookCategory;

import pl.tomekreda.library.model.book.BookCategory;

public class CreateBookCategory {
    public static BookCategory bookCategory() {
        BookCategory bookCategory=new BookCategory("Przygodowa");
        return bookCategory;
    }
}
