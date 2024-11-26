package ru.nihongo.study.service.utils;

import org.springframework.stereotype.Component;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.enumeration.ReviewAction;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Component
public class FSRSCalculator {

    // Параметры модели FSRS
    private static final double DEFAULT_DIFFICULTY = 1;
    private static final double MIN_DIFFICULTY = 0.1;
    private static final double MAX_DIFFICULTY = 10;

    private static final double DEFAULT_STABILITY = 1.0;
    private static final double MIN_STABILITY = 0.1667; // 4 часа в днях
    private static final double MAX_STABILITY = 180.0;  // 180 дней

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

        double newStability;
        if (rating == Rating.AGAIN) {
            // Для 'Again' устанавливаем стабильность на 1 минуту (1/1440 дня)
            newStability = 1.0 / 1440.0;
        } else {
            // Обновление стабильности
            newStability = calculateStability(stability, difficulty, rating);
            // Ограничиваем стабильность между MIN и MAX
            newStability = Math.max(MIN_STABILITY, Math.min(MAX_STABILITY, newStability));
        }

        // Обновление сложности
        double newDifficulty = calculateDifficulty(difficulty, rating);

        // Установка новых значений
        userCard.setDifficulty(newDifficulty);
        userCard.setStability(newStability);
        userCard.setLastReviewed(LocalDateTime.now());

        if (rating == Rating.AGAIN) {
            // Устанавливаем следующий повтор через 1 минуту
            userCard.setNextReview(LocalDateTime.now().plusMinutes(1));
        } else {
            // Вычисляем интервал до следующего повторения в секундах
            long intervalSeconds = (long) (newStability * 86400L);
            // Устанавливаем следующий повтор
            userCard.setNextReview(LocalDateTime.now().plusSeconds(intervalSeconds));
        }

        userCard.setNew(false); // Карточка больше не считается новой
    }

    // Преобразование ReviewAction в Rating
    private Rating getRatingFromAction(ReviewAction action) {
        return switch (action) {
            case HARD -> Rating.HARD;
            case GOOD -> Rating.GOOD;
            case EASY -> Rating.EASY;
            default -> Rating.AGAIN;
        };
    }

    // Функция обновления сложности
    private double calculateDifficulty(double oldDifficulty, Rating rating) {
        double deltaDifficulty = 0.0;
        switch (rating) {
            case AGAIN:
                deltaDifficulty = 0.0; // Не изменяем сложность
                break;
            case HARD:
                deltaDifficulty = +0.3; // Значительно увеличиваем сложность
                break;
            case GOOD:
                deltaDifficulty = -0.1; // Немного уменьшаем сложность
                break;
            case EASY:
                deltaDifficulty = -0.3; // Сильно уменьшаем сложность
                break;
        }
        double newDifficulty = oldDifficulty + deltaDifficulty;
        newDifficulty = Math.max(MIN_DIFFICULTY, Math.min(MAX_DIFFICULTY, newDifficulty));
        return newDifficulty;
    }

    // Функция обновления стабильности
    private double calculateStability(double oldStability, double difficulty, Rating rating) {
        double factor;
        switch (rating) {
            case HARD:
                factor = 0.5; // Уменьшаем интервал для HARD
                break;
            case GOOD:
                factor = 1.5; // Увеличиваем интервал для GOOD
                break;
            case EASY:
                factor = 3.0; // Более агрессивное увеличение интервала для EASY
                break;
            default:
                factor = 1.0;
                break;
        }

        // Расчет новой стабильности с учетом сложности
        double newStability = (oldStability * factor) / difficulty;

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
}
