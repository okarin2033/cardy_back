package ru.nihongo.study.adapter.feign.dto;

import lombok.Data;

import java.util.List;

@Data
public class TranslationResponse {
    private List<Translation> translations;

    @Data
    public static class Translation {
        private String text;
    }
}