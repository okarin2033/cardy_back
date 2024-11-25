package ru.nihongo.study.entity;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UserCardId implements Serializable {
    private Long userId;
    private Long cardId;
}