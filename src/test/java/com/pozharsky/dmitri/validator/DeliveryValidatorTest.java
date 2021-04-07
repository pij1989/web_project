package com.pozharsky.dmitri.validator;

import org.testng.annotations.Test;

import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;
import static org.testng.Assert.assertTrue;

public class DeliveryValidatorTest {

    @Test
    public void testIsValidDeliveryForm() {
        Map<String, String> deliveryForm = Map.of(FIRST_NAME, "Ivan", LAST_NAME, "Ivanov", CITY, "Minsk",
                STREET, "Kalinovskogo",HOME_NUMBER,"50",PHONE, "+375(29)5398751");
        boolean condition = DeliveryValidator.isValidDeliveryForm(deliveryForm);
        assertTrue(condition);
    }
}