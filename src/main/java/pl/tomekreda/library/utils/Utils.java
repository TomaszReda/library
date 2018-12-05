package pl.tomekreda.library.utils;

import pl.tomekreda.library.model.book.BookState;

public class Utils {


    public String convert(BookState bookState) {
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
}
