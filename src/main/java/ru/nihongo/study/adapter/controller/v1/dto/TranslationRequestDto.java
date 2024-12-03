package ru.nihongo.study.adapter.controller.v1.dto;

import lombok.Data;
import ru.nihongo.study.entity.enumeration.Language;

@Data
public class TranslationRequestDto {
    private Language sourceLanguage;
    private Language targetLanguage;
    private String text;
}
