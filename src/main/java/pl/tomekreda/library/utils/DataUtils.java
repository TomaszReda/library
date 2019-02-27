package pl.tomekreda.library.utils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.thymeleaf.util.DateUtils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
@Data
public class DataUtils {



    public static Date convertToDateViaInstant(LocalDateTime dateToConvert) {
        return java.util.Date
                .from(dateToConvert.atZone(ZoneId.systemDefault())
                        .toInstant());
    }
}
