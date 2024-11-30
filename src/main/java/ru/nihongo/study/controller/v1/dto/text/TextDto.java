package ru.nihongo.study.controller.v1.dto.text;

import lombok.Data;
import ru.nihongo.study.controller.v1.dto.EnumDto;
import ru.nihongo.study.entity.enumeration.Language;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class TextDto {
    private Long id;
    private String title;
    private String content;
    private EnumDto<Language> language;
    private LocalDateTime createdAt;
    private List<Long> userIds;
}
