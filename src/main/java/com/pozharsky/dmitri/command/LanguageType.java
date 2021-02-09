package com.pozharsky.dmitri.command;

public enum LanguageType {
    RU("ru"),
    EN("en");

    private String language;

    LanguageType(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return language;
    }
}
