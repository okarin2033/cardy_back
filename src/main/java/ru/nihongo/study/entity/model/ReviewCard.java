package ru.nihongo.study.entity.model;

import lombok.Data;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.enumeration.ReviewMode;

@Data
public class ReviewCard {
    private Long cardId;
    private String front;
    private String back;
    private String hint;
    private ReviewMode mode;

    public static ReviewCard createFromUserCard(UserCard userCard, ReviewMode mode) {
        ReviewCard card = new ReviewCard();
        card.setCardId(userCard.getId().getCardId());
        card.setFront(userCard.getCard().getFront());
        card.setBack(userCard.getCard().getBack());
        card.setHint(userCard.getCard().getHint());
        card.setMode(mode);

        return card;
    }
}
