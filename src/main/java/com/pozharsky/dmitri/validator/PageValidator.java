package com.pozharsky.dmitri.validator;

/**
 * Page validator class used to check pagination values.
 *
 * @author Dmitri Pozharsky
 */
public class PageValidator {
    private static final String PAGE_REGEX = "^(?!0)[0-9]{1,5}$";

    private PageValidator() {
    }

    public static boolean isValidPage(String page, String perPage) {
        boolean isValid = true;
        if (page == null || perPage == null) {
            isValid = false;
        } else {
            if (!page.matches(PAGE_REGEX) || !perPage.matches(PAGE_REGEX)) {
                isValid = false;
            }
        }
        return isValid;
    }
}
