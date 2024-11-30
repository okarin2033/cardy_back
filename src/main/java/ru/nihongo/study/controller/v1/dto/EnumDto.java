package ru.nihongo.study.controller.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EnumDto<T> {
    private T code;
    private String value;
}
