package com.pozharsky.dmitri.validator;

import com.pozharsky.dmitri.controller.command.RequestParameter;

import java.util.Map;

public class UserValidator {
    private static final String EMPTY = "";
    private static final String NAME_REGEX = "[a-zA-Zа-яА-Я]+";
    private static final String USERNAME_REGEX = "\\w+";
    private static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9@#$%!]{8,40}";

    private UserValidator() {
    }

    public static boolean isEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public static boolean isPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }

    public static boolean isValidRegistrationForm(Map<String, String> form) {
        boolean isValid = true;
        String firstName = form.get(RequestParameter.FIRST_NAME);
        if (!firstName.matches(NAME_REGEX)) {
            form.put(RequestParameter.FIRST_NAME, EMPTY);
            isValid = false;
        }
        String lastName = form.get(RequestParameter.LAST_NAME);
        if (!lastName.matches(NAME_REGEX)) {
            form.put(RequestParameter.LAST_NAME, EMPTY);
            isValid = false;
        }
        String username = form.get(RequestParameter.USERNAME);
        if (!username.matches(USERNAME_REGEX)) {
            form.put(RequestParameter.USERNAME, EMPTY);
            isValid = false;
        }
        String email = form.get(RequestParameter.EMAIL);
        if (!email.matches(EMAIL_REGEX)) {
            form.put(RequestParameter.EMAIL, EMPTY);
            isValid = false;
        }
        String password = form.get(RequestParameter.PASSWORD);
        if (!password.matches(PASSWORD_REGEX)) {
            form.put(RequestParameter.PASSWORD, EMPTY);
            isValid = false;
        }
        String confirmPassword = form.get(RequestParameter.CONFIRM_PASSWORD);
        if (!confirmPassword.matches(PASSWORD_REGEX) || !password.equals(confirmPassword)) {
            form.put(RequestParameter.CONFIRM_PASSWORD, EMPTY);
            isValid = false;
        }
        return isValid;
    }
}
