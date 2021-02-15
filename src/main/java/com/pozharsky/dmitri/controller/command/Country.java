package com.pozharsky.dmitri.controller.command;

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
