package com.pozharsky.dmitri.util;

import com.pozharsky.dmitri.controller.command.Country;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public final class FormatCurrencyUtil {
    private static final double COURSE = 2.6;

    private FormatCurrencyUtil() {
    }

    public static String formatCurrency(BigDecimal value, Locale locale) {
        if (locale.getCountry().equals(Country.US.getCountry())) {
            value = value.divide(new BigDecimal(COURSE), RoundingMode.HALF_UP);
        }
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance(locale);
        return numberFormat.format(value);
    }
}
