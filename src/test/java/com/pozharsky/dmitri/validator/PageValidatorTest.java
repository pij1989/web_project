package com.pozharsky.dmitri.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.*;

public class PageValidatorTest {

    @Test
    public void testIsValidPageTrue() {
        String page = "1";
        String perPage = "5";
        boolean condition = PageValidator.isValidPage(page,perPage);
        assertTrue(condition);
    }

    @Test
    public void testIsValidPageFalse() {
        String page = "Hello";
        String perPage = "World";
        boolean condition = PageValidator.isValidPage(page,perPage);
        assertFalse(condition);
    }
}