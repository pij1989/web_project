package com.pozharsky.dmitri.model.creator;

import com.pozharsky.dmitri.model.entity.Address;
import com.pozharsky.dmitri.model.entity.Delivery;

import java.util.Map;

import static com.pozharsky.dmitri.controller.command.RequestParameter.*;

public class DeliveryCreator {

    private DeliveryCreator() {
    }

    public static Delivery createDelivery(Map<String, String> deliveryForm, long orderId) {
        String firstName = deliveryForm.get(FIRST_NAME);
        String lastName = deliveryForm.get(LAST_NAME);
        String city = deliveryForm.get(CITY);
        String street = deliveryForm.get(STREET);
        String homeNumber = deliveryForm.get(HOME_NUMBER);
        String phone = deliveryForm.get(PHONE);
        Address address = new Address(city, street, homeNumber);
        return new Delivery(firstName, lastName, address, phone, orderId);
    }
}
