package com.pozharsky.dmitri.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class FormatCurrencyUtil {
    private static final double COURSE_BY_USD = 2.6;

    private FormatCurrencyUtil() {
    }

    public static String formatCurrency(BigDecimal value, Locale locale) {
        if (locale.equals(Locale.US)) {
            value = value.divide(new BigDecimal(COURSE_BY_USD), RoundingMode.HALF_UP);
        }
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(value);
    }
}
