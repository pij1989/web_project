package com.pozharsky.dmitri.validator;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

public class CategoryValidatorTest {

    @Test
    public void testIsCategoryName() {
        String categoryName = "Processor";
        boolean condition = CategoryValidator.isCategoryName(categoryName);
        assertTrue(condition);
    }
}