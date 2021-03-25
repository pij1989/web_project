package com.pozharsky.dmitri.model.entity;

public class Delivery extends Entity {
    private long id;
    private String firstName;
    private String lastName;
    private Address address;
    private String phoneNumber;
    private long orderId;

    public Delivery() {
    }

    public Delivery(String firstName, String lastName, Address address, String phoneNumber, long orderId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.orderId = orderId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Delivery delivery = (Delivery) o;

        if (id != delivery.id) return false;
        if (orderId != delivery.orderId) return false;
        if (firstName != null ? !firstName.equals(delivery.firstName) : delivery.firstName != null) return false;
        if (lastName != null ? !lastName.equals(delivery.lastName) : delivery.lastName != null) return false;
        if (address != null ? !address.equals(delivery.address) : delivery.address != null) return false;
        return phoneNumber != null ? phoneNumber.equals(delivery.phoneNumber) : delivery.phoneNumber == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstName != null ? firstName.hashCode() : 0);
        result = 31 * result + (lastName != null ? lastName.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (int) (orderId ^ (orderId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Delivery{");
        sb.append("id=").append(id);
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", address=").append(address);
        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
        sb.append(", orderId=").append(orderId);
        sb.append('}');
        return sb.toString();
    }
}
