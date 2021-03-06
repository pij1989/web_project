package com.pozharsky.dmitri.validator;

import com.pozharsky.dmitri.controller.command.RequestParameter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * Product validator class used to check product fields values.
 *
 * @author Dmitri Pozharsky
 */
public class ProductValidator {
    private static final Logger logger = LogManager.getLogger(ProductValidator.class);
    private static final String EMPTY = "";
    private static final String NAME_REGEX = "[\\-\\s\\w]+";
    private static final String PRICE_REGEX = "^[0-9]{1,4}\\.[0-9]{2}$";
    private static final String AMOUNT_REGEX = "^[0-9]{1,5}$";

    private ProductValidator() {
    }

    public static boolean isValidProductForm(Map<String, String> form) {
        boolean isValid = true;
        String productName = form.get(RequestParameter.PRODUCT_NAME);
        if (!productName.matches(NAME_REGEX)) {
            isValid = false;
            form.put(RequestParameter.PRODUCT_NAME, EMPTY);
        }
        String price = form.get(RequestParameter.PRICE);
        if (!price.matches(PRICE_REGEX)) {
            isValid = false;
            form.put(RequestParameter.PRICE, EMPTY);
        }
        String amount = form.get(RequestParameter.AMOUNT);
        if (!amount.matches(AMOUNT_REGEX)) {
            isValid = false;
            form.put(RequestParameter.AMOUNT, EMPTY);
        }
        String timeCreate = form.get(RequestParameter.TIME_CREATE);
        if (!validateDateTime(timeCreate)) {
            isValid = false;
            form.put(RequestParameter.TIME_CREATE, EMPTY);
        }
        return isValid;
    }

    public static boolean isValidFilterProductForm(Map<String, String> filterForm) {
        boolean isValid = true;
        String priceFrom = filterForm.get(RequestParameter.PRICE_FROM);
        if (!priceFrom.matches(PRICE_REGEX) && !priceFrom.equals(EMPTY)) {
            isValid = false;
        }
        String priceTo = filterForm.get(RequestParameter.PRICE_TO);
        if (!priceTo.matches(PRICE_REGEX) && !priceTo.equals(EMPTY)) {
            isValid = false;
        }
        return isValid;
    }

    private static boolean validateDateTime(String dateTime) {
        try {
            LocalDateTime.parse(dateTime, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
            return true;
        } catch (Exception e) {
            logger.error(e);
            return false;
        }
    }
}
