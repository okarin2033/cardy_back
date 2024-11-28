package ru.nihongo.study.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nihongo.study.entity.Card;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.repository.CardRepository;
import ru.nihongo.study.repository.UserCardRepository;
import ru.nihongo.study.service.utils.SecurityUtil;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private UserCardRepository userCardRepository;

    //Общие методы для карточек

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    public List<Card> getCardsByDeckId(Long deckId) {
        return cardRepository.findByDeckId(deckId);
    }

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new EntityNotFoundException("CardService" + cardId));
    }

    //Методы по юзеру

    public long getCardsForReviewCount(Long deckId) {
        return userCardRepository.countCardsToReview(LocalDateTime.now(), deckId,
            SecurityUtil.getcurrentUserInfo(), Boolean.FALSE);
    }

    public List<UserCard> getUserCardsByDeckId(Long deckId) {
        UserInfo user = SecurityUtil.getcurrentUserInfo();
        return userCardRepository.findByUserAndCardDeckId(user, deckId);
    }
}
