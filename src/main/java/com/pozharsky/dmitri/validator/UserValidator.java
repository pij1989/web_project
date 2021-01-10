package com.pozharsky.dmitri.validator;

public class UserValidator {
    private static final String EMAIL_REGEX = "^([a-z0-9_-]+\\.)*[a-z0-9_-]+@[a-z0-9_-]+(\\.[a-z0-9_-]+)*\\.[a-z]{2,6}$";
    private static final String PASSWORD_REGEX = "[a-zA-Z0-9@#$%!]{8,40}";
    public static final UserValidator INSTANCE = new UserValidator();

    private UserValidator() {
    }

    public boolean isEmail(String email) {
        return email.matches(EMAIL_REGEX);
    }

    public boolean isPassword(String password) {
        return password.matches(PASSWORD_REGEX);
    }
}
