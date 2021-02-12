package com.pozharsky.dmitri.command;

public enum Language {
    RU("ru"),
    EN("en");

    private String language;

    Language(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
