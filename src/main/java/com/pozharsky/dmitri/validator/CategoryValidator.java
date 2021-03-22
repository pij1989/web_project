package com.pozharsky.dmitri.validator;

public class CategoryValidator {
    private static final String NAME_REGEX = "[\\-\\s\\w]+";

    private CategoryValidator() {
    }

    public static boolean isCategoryName(String categoryName) {
        if (categoryName != null) {
            return categoryName.matches(NAME_REGEX);
        }
        return false;
    }
}
