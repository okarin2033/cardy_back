package ru.nihongo.study.controller.v1.dto.deck;

import lombok.Data;
import ru.nihongo.study.controller.v1.dto.card.CardDto;

import java.util.List;

@Data
public class DeckDto {
    private Long id;
    private String name;
    private Long count;
    private Long needReview;
}