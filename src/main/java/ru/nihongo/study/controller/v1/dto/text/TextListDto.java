package ru.nihongo.study.controller.v1.dto.text;

import lombok.Data;
import ru.nihongo.study.controller.v1.dto.EnumDto;

import java.time.LocalDateTime;

@Data
public class TextListDto {
    private Long id;
    private String title;
    private EnumDto<String> language;
    private LocalDateTime createdAt;
}
