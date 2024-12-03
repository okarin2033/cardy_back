package ru.nihongo.study.entity.enumeration;

import lombok.Getter;

@Getter
public enum Language {
    JAPANESE("Японский", "JA"),
    ENGLISH("Английский", "EN"),
    RUSSIAN("Русский", "RU"),
    CIGAN("Циганский", "ZV");

    private final String value;
    private final String deeplCode;

    Language(String name, String deeplCode) {
        this.value = name;
        this.deeplCode = deeplCode;
    }
}
