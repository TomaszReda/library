package pl.tomekreda.library.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DataUtils {

    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
