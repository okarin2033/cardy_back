package ru.nihongo.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nihongo.study.entity.Card;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.UserCardId;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.entity.enumeration.ReviewAction;
import ru.nihongo.study.repository.DeckRepository;
import ru.nihongo.study.repository.UserCardRepository;
import ru.nihongo.study.repository.UserInfoRepository;
import ru.nihongo.study.service.utils.FSRSCalculator;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    @Autowired
    private UserCardRepository userCardRepository;
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private FSRSCalculator fsrsCalculator;

    @Transactional
    public List<UserCard> getCardsForReview(Long deckId, Long userId) {
        UserInfo user = userInfoRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        Deck deck = deckRepository.findById(deckId)
            .orElseThrow(() -> new RuntimeException("Deck not found"));

        List<Card> cards = deck.getCards();
        List<UserCard> cardsForReview = new ArrayList<>();

        for (Card card : cards) {
            UserCardId userCardId = new UserCardId();
            userCardId.setUserId(userId);
            userCardId.setCardId(card.getId());

            UserCard userCard = userCardRepository.findById(userCardId)
                .orElseGet(() -> {
                    // Карточка новая для пользователя
                    UserCard newUserCard = new UserCard();
                    newUserCard.setId(userCardId);
                    newUserCard.setUser(user);
                    newUserCard.setCard(card);
                    newUserCard.setRepetition(0);
                    newUserCard.setInterval(0);
                    newUserCard.setEaseFactor(2.5);
                    newUserCard.setLastReviewed(null);
                    newUserCard.setNextReview(LocalDateTime.now());
                    newUserCard.setStatus(0); // Статус новой карточки
                    newUserCard.setNew(true); // Помечаем как новую
                    userCardRepository.save(newUserCard);
                    return newUserCard;
                });

            if (fsrsCalculator.isDueForReview(userCard)) {
                cardsForReview.add(userCard);
            }
        }

        return cardsForReview;
    }

    @Transactional
    public void updateCardReview(Long cardId, ReviewAction action) {
        UserCardId userCardId = new UserCardId();
        userCardId.setCardId(cardId);
        userCardId.setUserId(0L);

        UserCard userCard = userCardRepository.findById(userCardId)
            .orElseThrow(() -> new RuntimeException("UserCard not found"));

        fsrsCalculator.updateUserCard(userCard, action);

        userCardRepository.save(userCard);
    }
}
