package com.pozharsky.dmitri.validator;

import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

/**
 * Delivery validator class used to check delivery fields values.
 *
 * @author Dmitri Pozharsky
 */
public class DeliveryValidator {
    private static final String EMPTY = "";
    private static final String NAME_REGEX = "[a-zA-Zа-яА-Я]+";
    private static final String CITY_REGEX = "^[A-ZА-Я](?:(-?|\\s?)[a-zA-Zа-яА-Я]+)*$";
    private static final String STREET_REGEX = "^([0-9]+|[A-ZА-Я])(?:(-?|\\s?)[a-zA-Zа-яА-Я0-9]+)*$";
    private static final String HOME_NUMBER_REGEX = "^(?!0)[0-9]{1,3}(\\/(?!0)[0-9]){0,1}$";
    private static final String PHONE_REGEX = "^[+]{1}([375]){3}[(]{1}[0-9]{2}[)]{1}[0-9]{7}$";

    private DeliveryValidator() {
    }

    public static boolean isValidDeliveryForm(Map<String, String> deliveryForm) {
        boolean isValid = true;
        String firstName = deliveryForm.get(FIRST_NAME);
        if (!firstName.matches(NAME_REGEX)) {
            deliveryForm.put(FIRST_NAME, EMPTY);
            isValid = false;
        }
        String lastName = deliveryForm.get(LAST_NAME);
        if (!lastName.matches(NAME_REGEX)) {
            deliveryForm.put(LAST_NAME, EMPTY);
            isValid = false;
        }
        String city = deliveryForm.get(CITY);
        if (!city.matches(CITY_REGEX)) {
            deliveryForm.put(CITY, EMPTY);
            isValid = false;
        }
        String street = deliveryForm.get(STREET);
        if (!street.matches(STREET_REGEX)) {
            deliveryForm.put(STREET, EMPTY);
            isValid = false;
        }
        String homeNumber = deliveryForm.get(HOME_NUMBER);
        if (!homeNumber.matches(HOME_NUMBER_REGEX)) {
            deliveryForm.put(HOME_NUMBER, EMPTY);
            isValid = false;
        }
        String phone = deliveryForm.get(PHONE);
        if (!phone.matches(PHONE_REGEX)) {
            deliveryForm.put(PHONE, EMPTY);
            isValid = false;
        }
        return isValid;
    }
}
