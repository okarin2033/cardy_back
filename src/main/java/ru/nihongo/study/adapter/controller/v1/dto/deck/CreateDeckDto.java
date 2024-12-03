package ru.nihongo.study.adapter.controller.v1.dto.deck;

import lombok.Data;

@Data
public class CreateDeckDto {
    private String name;
    private Long userId;
}