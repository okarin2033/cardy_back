package ru.nihongo.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nihongo.study.entity.Card;
import ru.nihongo.study.repository.CardRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }

    public List<Card> getCardsByDeckId(Long deckId) {
        return cardRepository.findByDeckId(deckId);
    }

    public Optional<Card> getCardById(Long cardId) {
        return cardRepository.findById(cardId);
    }
}
