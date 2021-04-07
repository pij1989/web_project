package com.pozharsky.dmitri.validator;

import org.testng.annotations.Test;

import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ProductValidatorTest {

    @Test
    public void testIsValidProductForm() {
        Map<String, String> productForm = Map.of(PRODUCT_NAME, "Intel Core i5", PRICE, "600.00",
                AMOUNT, "20", TIME_CREATE, "2020-12-03T10:15:30");
        boolean condition = ProductValidator.isValidProductForm(productForm);
        assertTrue(condition);
    }

    @Test
    public void testIsValidFilterProductFormTrue() {
        Map<String, String> filterForm = Map.of(PRICE_FROM, "200.00", PRICE_TO, "600.00");
        boolean condition = ProductValidator.isValidFilterProductForm(filterForm);
        assertTrue(condition);
    }

    @Test
    public void testIsValidFilterProductFormFalse() {
        Map<String, String> filterForm = Map.of(PRICE_FROM, "Hello", PRICE_TO, "World");
        boolean condition = ProductValidator.isValidFilterProductForm(filterForm);
        assertFalse(condition);
    }
}