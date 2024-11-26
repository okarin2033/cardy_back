package ru.nihongo.study.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_cards")
@Getter
@Setter
public class UserCard {
    @EmbeddedId
    private UserCardId id;

    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private UserInfo user;

    @MapsId("cardId")
    @JoinColumn(name = "card_id")
    @ManyToOne(cascade = CascadeType.ALL)
    private Card card;

    // Поля для FSRS
    private int repetition; // Количество повторений
    private double interval; // Интервал повторений (дни)
    private double easeFactor; // Коэффициент легкости
    private LocalDateTime lastReviewed; // Дата последнего повторения
    private LocalDateTime nextReview; // Дата следующего повторения
    private int status; // Статус карточки (например, активна, изучена и т.д.)

    private boolean isNew;
}