package com.pozharsky.dmitri.model.entity;

/**
 * The type Address.
 *
 * @author Dmitri Pozharsky
 */
public class Address {
    private String city;
    private String street;
    private String homeNumber;

    /**
     * Instantiates a new Address.
     *
     * @param city       the city
     * @param street     the street
     * @param homeNumber the home number
     */
    public Address(String city, String street, String homeNumber) {
        this.city = city;
        this.street = street;
        this.homeNumber = homeNumber;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets street.
     *
     * @return the street
     */
    public String getStreet() {
        return street;
    }

    /**
     * Sets street.
     *
     * @param street the street
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * Gets home number.
     *
     * @return the home number
     */
    public String getHomeNumber() {
        return homeNumber;
    }

    /**
     * Sets home number.
     *
     * @param homeNumber the home number
     */
    public void setHomeNumber(String homeNumber) {
        this.homeNumber = homeNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (city != null ? !city.equals(address.city) : address.city != null) return false;
        if (street != null ? !street.equals(address.street) : address.street != null) return false;
        return homeNumber != null ? homeNumber.equals(address.homeNumber) : address.homeNumber == null;
    }

    @Override
    public int hashCode() {
        int result = city != null ? city.hashCode() : 0;
        result = 31 * result + (street != null ? street.hashCode() : 0);
        result = 31 * result + (homeNumber != null ? homeNumber.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Address{");
        sb.append("city='").append(city).append('\'');
        sb.append(", street='").append(street).append('\'');
        sb.append(", home='").append(homeNumber).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
