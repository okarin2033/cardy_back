package ru.nihongo.study.adapter.controller.v1.dto.card;

import lombok.Data;
import ru.nihongo.study.entity.enumeration.ReviewMode;

@Data
public class ReviewCardDto {
    private Long cardId;
    private String front;
    private String back;
    private String hint;
    private ReviewMode mode;
}
