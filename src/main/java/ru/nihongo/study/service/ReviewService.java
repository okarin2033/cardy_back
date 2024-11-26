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
import ru.nihongo.study.service.utils.SecurityUtil;

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
    public List<UserCard> getCardsForReview(Long deckId) {
        Long userId = SecurityUtil.getCurrentUserId();
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
                    fsrsCalculator.initializeUserCard(newUserCard);
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
        UserCardId userCardId = new UserCardId(SecurityUtil.getCurrentUserId(), cardId);

        UserCard userCard = userCardRepository.findById(userCardId)
            .orElseThrow(() -> new RuntimeException("UserCard not found"));

        fsrsCalculator.updateUserCard(userCard, action);

        userCardRepository.save(userCard);
    }
}
