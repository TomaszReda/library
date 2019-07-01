package pl.tomekreda.library.test.helpers.book;

import pl.tomekreda.library.model.book.Book;
import pl.tomekreda.library.model.book.BookState;
import pl.tomekreda.library.test.helpers.bookCategory.CreateBookCategory;
import pl.tomekreda.library.test.helpers.library.CreateLibrary;
import pl.tomekreda.library.test.helpers.user.CreateUser;

import java.time.LocalDate;

public class CreateBook {
    public static Book createBook() {
        Book book = new Book(null, "Henryk Sienkiewicz", "W pustyni i w puszczy", "PWD", LocalDate.of(1992, 12, 11), "12213342", 1, "Henryk Sienkiewicz W pustyni i w puszczy", null, "Opis", CreateLibrary.createLibray(), BookState.NOTRESERVED, null, CreateUser.createLibraryOwner().getUserMenager(), CreateBookCategory.bookCategory());
        return book;
    }
}
