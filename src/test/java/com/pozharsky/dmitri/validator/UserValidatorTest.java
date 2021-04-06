package com.pozharsky.dmitri.validator;

import org.testng.annotations.Test;

import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class UserValidatorTest {

    @Test
    public void testIsEmailTrue() {
        String email = "user@gmail.com";
        boolean condition = UserValidator.isEmail(email);
        assertTrue(condition);
    }

    @Test
    public void testIsEmailFalse() {
        String email = "user@gmail.com...";
        boolean condition = UserValidator.isEmail(email);
        assertFalse(condition);
    }

    @Test
    public void testIsPasswordTrue() {
        String password = "12345678";
        boolean condition = UserValidator.isPassword(password);
        assertTrue(condition);
    }

    @Test
    public void testIsPasswordFalse() {
        String password = "123";
        boolean condition = UserValidator.isPassword(password);
        assertFalse(condition);
    }

    @Test
    public void testIsValidRegistrationForm() {
        Map<String, String> userMap = Map.of(FIRST_NAME, "Ivan", LAST_NAME, "Ivanov", USERNAME, "vaness",
                EMAIL, "ivan@gmail.com", PASSWORD, "12345678",CONFIRM_PASSWORD,"12345678");
        boolean condition = UserValidator.isValidRegistrationForm(userMap);
        assertTrue(condition);
    }
}