package ru.nihongo.study.adapter.controller.v1.dto.text;

import lombok.Data;
import ru.nihongo.study.entity.enumeration.Language;

@Data
public class CreateTextDto {
    private String title;
    private String content;
    private Language language;
}
