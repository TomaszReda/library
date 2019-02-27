package pl.tomekreda.library.utils;

import lombok.extern.slf4j.Slf4j;

public final class MessageUtils {

    private MessageUtils() {
    //not caled
    }

    public static final String MESSAGE_RESERV_BOOK_TO_LIBRARY_OWNER_TITLE = "Rezerwacja książki przez użytkownika!";

    public static final String MESSAGE_RESERV_BOOK_TO_CASUAL_USER_TITLE = "Moja rezerwacja książki!";

    public static final String MESSAGE_RESIGNATION_RESERV_BOOK_TO_LIBRARY_OWNER_TITLE = "Rezygnacja z zarezerwowanej książki!";

    public static final String MESSAGE_ACCEPT_RESERV_BOOK_TO_CASUAL_USER_TITLE = "Potwierdzenie rezerwacji!";

    public static final String MESSAGE_REJECTED_RESERV_BOOK_TO_CASUAL_USER_TITLE = "Odrzucenie rezerwacji!";

    public static final String RETURN_BOOK_TO_CASUAL_USER_TITLE = "Oddanie książki!";

    public static final String RETURN_BOOK_TO_LIBRARY_OWNER_TITLE = "Użytkownik oddał ksiązkę!";

    public static final String RESERV_NOT_RECEIVED_FOR_CASUAL_USER = "Rezerwacja wygasła!";

    public static final String RESERV_NOT_RECEIVED_FOR_LIBRARY_OWNER = "Rezerwacja nie odebrana!";

    public static final String REMINDER_OF_GIVING_A_BOOK_FOR_CASUAL_USER = "Przypomnienie o rezerwacji!";

    public static final String REMINDER_OF_GIVING_A_BOOK_FOR_LIBRARY_OWNER = "Przypomnienie o rezerwacji!";

    public static final String RESET_DATA_TOKEN = "Token do resetu hasła wygasł!";

}
