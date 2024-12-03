package ru.nihongo.study.entity.enumeration;

import lombok.Getter;

@Getter
public enum Language {
    JAPANESE("Japanese", "JA"),
    ENGLISH("English", "EN"),
    RUSSIAN("Russian", "RU");

    private final String value;
    private final String deeplCode;

    Language(String name, String deeplCode) {
        this.value = name;
        this.deeplCode = deeplCode;
    }
}
