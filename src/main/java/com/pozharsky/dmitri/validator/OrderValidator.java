package com.pozharsky.dmitri.validator;

public class OrderValidator {
    private static final String AMOUNT_REGEX = "^(?!0)[0-9]{1,5}$";

    private OrderValidator() {
    }

    public static boolean isValidAmountProduct(String amountProduct) {
        if (amountProduct != null) {
            return amountProduct.matches(AMOUNT_REGEX);
        }
        return false;
    }
}
