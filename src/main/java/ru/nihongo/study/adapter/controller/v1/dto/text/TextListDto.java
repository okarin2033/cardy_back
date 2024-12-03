package ru.nihongo.study.adapter.controller.v1.dto.text;

import lombok.Data;
import ru.nihongo.study.adapter.controller.v1.dto.EnumDto;
import ru.nihongo.study.entity.enumeration.Language;

import java.time.LocalDateTime;

@Data
public class TextListDto {
    private Long id;
    private String title;
    private EnumDto<Language> language;
    private LocalDateTime createdAt;
}
