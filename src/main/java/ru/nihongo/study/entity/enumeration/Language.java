package ru.nihongo.study.entity.enumeration;

public enum Language {
    JAPANESE("Japanese"),
    ENGLISH("English"),
    RUSSIAN("Russian");

    private final String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
