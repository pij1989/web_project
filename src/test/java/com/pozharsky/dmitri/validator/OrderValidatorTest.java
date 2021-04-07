package com.pozharsky.dmitri.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class OrderValidatorTest {

    @Test
    public void testIsValidAmountProductTrue() {
        String amountProduct = "20";
        boolean condition = OrderValidator.isValidAmountProduct(amountProduct);
        assertTrue(condition);
    }

    @Test
    public void testIsValidAmountProductFalse() {
        String amountProduct = "Hello";
        boolean condition = OrderValidator.isValidAmountProduct(amountProduct);
        assertFalse(condition);
    }
}