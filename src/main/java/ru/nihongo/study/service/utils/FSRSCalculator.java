package ru.nihongo.study.service.utils;

import org.springframework.stereotype.Component;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.enumeration.ReviewAction;

import java.time.LocalDateTime;

@Component
public class FSRSCalculator {

    public boolean isDueForReview(UserCard userCard) {
        if (userCard.isNew()) {
            return true; // Новые карточки всегда доступны для изучения
        }
        return userCard.getNextReview().isBefore(LocalDateTime.now()) ||
            userCard.getNextReview().isEqual(LocalDateTime.now());
    }

    public void updateUserCard(UserCard userCard, ReviewAction action) {
        double easeFactor = userCard.getEaseFactor();
        int repetition = userCard.getRepetition();
        double interval = userCard.getInterval();

        switch (action) {
            case AGAIN: // Снова
                repetition = 0;
                interval = 1;
                easeFactor = Math.max(1.3, easeFactor - 0.2);
                break;
            case HARD: // Трудно
                repetition++;
                interval = interval * 1.2;
                easeFactor = Math.max(1.3, easeFactor - 0.15);
                break;
            case GOOD: // Хорошо
                repetition++;
                interval = interval * easeFactor;
                // easeFactor остаётся без изменений
                break;
            case EASY: // Легко
                repetition++;
                interval = interval * easeFactor * 1.5;
                easeFactor = Math.min(2.5, easeFactor + 0.1);
                break;
        }

        userCard.setRepetition(repetition);
        userCard.setInterval(interval);
        userCard.setEaseFactor(easeFactor);
        userCard.setLastReviewed(LocalDateTime.now());
        userCard.setNextReview(LocalDateTime.now().plusDays((long) interval));
        userCard.setNew(false); // После первого повторения карточка больше не новая
    }
}