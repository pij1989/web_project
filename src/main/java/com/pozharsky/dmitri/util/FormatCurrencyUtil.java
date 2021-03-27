package com.pozharsky.dmitri.util;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class FormatCurrencyUtil {

    private FormatCurrencyUtil() {
    }

    public static String formatCurrency(BigDecimal value, Locale locale) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(value);
    }
}
