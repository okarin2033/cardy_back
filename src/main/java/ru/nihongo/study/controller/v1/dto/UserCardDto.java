package ru.nihongo.study.controller.v1.dto;

import lombok.Data;

@Data
public class UserCardDto {
    private Long cardId;
    private String front;
    private String back;
    private String hint;
    private boolean isNew;
}