package ru.nihongo.study.adapter.controller.v1.dto;

import lombok.Getter;
import lombok.Setter;
import ru.nihongo.study.entity.enumeration.ReviewAction;

@Getter
@Setter
public class ReviewActionDto {
    private Long userCardId;
    private ReviewAction action; // "AGAIN", "HARD", "GOOD", "EASY", "LEARN"
}