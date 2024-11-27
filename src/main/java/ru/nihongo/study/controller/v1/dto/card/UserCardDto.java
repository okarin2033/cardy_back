package ru.nihongo.study.controller.v1.dto.card;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class UserCardDto {
    private Long cardId;
    private String front;
    private String back;
    private String hint;
    private boolean isNew;
    
    // Поля для FSRS
    private double stability;
    private double difficulty;
    private LocalDateTime lastReviewed;
    private LocalDateTime nextReview;
}