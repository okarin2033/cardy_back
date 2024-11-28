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
import ru.nihongo.study.entity.enumeration.ReviewMode;
import ru.nihongo.study.entity.model.ReviewCard;
import ru.nihongo.study.repository.DeckRepository;
import ru.nihongo.study.repository.UserCardRepository;
import ru.nihongo.study.repository.UserInfoRepository;
import ru.nihongo.study.service.utils.FSRSCalculator;
import ru.nihongo.study.service.utils.SecurityUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public List<ReviewCard> getCardsForReview(Long deckId, String mode) {
        Long userId = SecurityUtil.getCurrentUserId();
        UserInfo user = userInfoRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        List<UserCard> currentCards = userCardRepository.findByUserAndCardDeckId(user, deckId).stream().filter(
            card -> fsrsCalculator.isDueForReview(card)
        ).toList();

        if (Objects.equals(mode, "mixed")) {
            return getCardsForMixed(currentCards.stream().filter(UserCard::isNew).toList());
        } else if (Objects.equals(mode, "review_only")) {
            return getCardsForReview(currentCards);
        }

        return Collections.emptyList();
    }

    @Transactional
    public void updateCardReview(Long cardId, ReviewAction action) {
        UserCardId userCardId = new UserCardId(SecurityUtil.getCurrentUserId(), cardId);

        UserCard userCard = userCardRepository.findById(userCardId)
            .orElseThrow(() -> new RuntimeException("UserCard not found"));
        if (action.equals(ReviewAction.LEARN)) {
            userCard.setNew(false);
        } else {
            fsrsCalculator.updateUserCard(userCard, action);
        }

        userCardRepository.save(userCard);
    }

    @Transactional
    public void initializeCardsForUser(Long deckId) {
        UserInfo user = SecurityUtil.getcurrentUserInfo();

        Deck deck = deckRepository.findById(deckId)
            .orElseThrow(() -> new RuntimeException("Deck not found"));

        List<Card> cards = deck.getCards();

        for (Card card : cards) {
            UserCardId userCardId = new UserCardId();
            userCardId.setUserId(user.getId());
            userCardId.setCardId(card.getId());

            if (!userCardRepository.existsById(userCardId)) {
                UserCard newUserCard = new UserCard();
                newUserCard.setId(userCardId);
                newUserCard.setUser(user);
                newUserCard.setCard(card);
                fsrsCalculator.initializeUserCard(newUserCard);
                userCardRepository.save(newUserCard);
            }
        }
    }

    // ===================================================================================================================
    // = Implementation
    // ===================================================================================================================

    private List<ReviewCard> getCardsForMixed(List<UserCard> cards) {
        int limit = Math.min(5, cards.size());
        List<ReviewCard> cardsForReview = new ArrayList<>();

        for (int i = 0; i < limit; i++) {
            UserCard card = cards.get(i);
            cardsForReview.add(ReviewCard.createFromUserCard(card, ReviewMode.LEARN));
        }

        for (int i = 0; i < limit; i++) {
            UserCard card = cards.get(i);
            cardsForReview.add(ReviewCard.createFromUserCard(card, ReviewMode.REVIEW));
        }

        return cardsForReview;
    }

    private List<ReviewCard> getCardsForReview(List<UserCard> cards) {
        return cards.stream()
            .filter(card -> !card.isNew())
            .map(card -> ReviewCard.createFromUserCard(card, ReviewMode.REVIEW))
            .toList();
    }
}
