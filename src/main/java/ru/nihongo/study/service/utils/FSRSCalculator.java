package ru.nihongo.study.service.utils;

import org.springframework.stereotype.Component;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.enumeration.ReviewAction;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class FSRSCalculator {

    // Параметры модели FSRS (могут быть настроены на основе исследований)
    private static final double DEFAULT_DIFFICULTY = 0.3;
    private static final double MIN_DIFFICULTY = 0.1;
    private static final double MAX_DIFFICULTY = 1.0;

    private static final double DEFAULT_STABILITY = 1.0;
    private static final double MIN_STABILITY = 0.1;

    // Оценки ответа пользователя
    public enum Rating {
        AGAIN(0),
        HARD(1),
        GOOD(2),
        EASY(3);

        private final int value;

        Rating(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    // Проверка, нуждается ли карточка в повторении
    public boolean isDueForReview(UserCard userCard) {
        if (userCard.isNew()) {
            return true; // Новые карточки всегда доступны для изучения
        }

        return !LocalDateTime.now().isBefore(userCard.getNextReview());
    }

    // Обновление параметров карточки после повторения
    public void updateUserCard(UserCard userCard, ReviewAction action) {
        // Получаем текущие значения
        double stability = userCard.getStability();
        double difficulty = userCard.getDifficulty();
        LocalDateTime lastReviewed = userCard.getLastReviewed();

        // Оценка ответа пользователя
        Rating rating = getRatingFromAction(action);

        // Вычисление времени прошедшего с последнего повторения
        long deltaDays = lastReviewed != null ? ChronoUnit.DAYS.between(lastReviewed, LocalDateTime.now()) : 0;

        // Обновление стабильности и сложности
        double newDifficulty = calculateDifficulty(difficulty, rating);
        double newStability = calculateStability(stability, newDifficulty, rating, deltaDays);

        // Установка новых значений
        userCard.setDifficulty(newDifficulty);
        userCard.setStability(newStability);
        userCard.setLastReviewed(LocalDateTime.now());
        userCard.setNextReview(LocalDateTime.now().plusSeconds((long) (newStability * 86400L)));
        userCard.setNew(false); // Карточка больше не считается новой
    }

    // Преобразование ReviewAction в Rating
    private Rating getRatingFromAction(ReviewAction action) {
        switch (action) {
            case AGAIN:
                return Rating.AGAIN;
            case HARD:
                return Rating.HARD;
            case GOOD:
                return Rating.GOOD;
            case EASY:
                return Rating.EASY;
            default:
                return Rating.AGAIN;
        }
    }

    // Функция обновления сложности
    private double calculateDifficulty(double oldDifficulty, Rating rating) {
        double newDifficulty = oldDifficulty + 0.1 - (3 - rating.getValue()) * 0.1;
        newDifficulty = Math.max(MIN_DIFFICULTY, Math.min(MAX_DIFFICULTY, newDifficulty));
        return newDifficulty;
    }

    // Функция обновления стабильности
    private double calculateStability(double oldStability, double difficulty, Rating rating, long deltaDays) {
        double factor = 1.0;
        switch (rating) {
            case AGAIN:
                factor = 0.5;
                break;
            case HARD:
                factor = 0.8;
                break;
            case GOOD:
                factor = 1.0;
                break;
            case EASY:
                factor = 1.2;
                break;
        }

        double newStability = (oldStability + deltaDays / difficulty) * factor;
        newStability = Math.max(MIN_STABILITY, newStability);
        return newStability;
    }

    // Инициализация новой карточки
    public void initializeUserCard(UserCard userCard) {
        userCard.setDifficulty(DEFAULT_DIFFICULTY);
        userCard.setStability(DEFAULT_STABILITY);
        userCard.setLastReviewed(LocalDateTime.now());
        userCard.setNextReview(LocalDateTime.now());
        userCard.setNew(true);
    }
}
