package com.pozharsky.dmitri.validator;

public final class CategoryValidator {
    private static final String NAME_REGEX = "[\\-\\s\\w]+";

    private CategoryValidator() {
    }

    public static boolean isCategoryName(String categoryName) {
        return categoryName.matches(NAME_REGEX);
    }
}
