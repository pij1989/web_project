package com.pozharsky.dmitri.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class FormatLocalDateTimeUtil {
    private static final String PATTERN = "dd-MMMM-yyyy HH:mm";

    private FormatLocalDateTimeUtil() {
    }

    public static String formatLocaleDateTime(LocalDateTime localDateTime, Locale locale) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(PATTERN, locale);
        return dateTimeFormatter.format(localDateTime);
    }
}
