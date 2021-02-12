package com.pozharsky.dmitri.command;

public enum Country {
    RU("RU"),
    US("US");

    private String country;

    Country(String country) {
        this.country = country;
    }

    public String getCountry() {
        return country;
    }
}
