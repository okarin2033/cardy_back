package ru.nihongo.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.repository.DeckRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    public Deck createDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    public Optional<Deck> getDeckById(Long deckId) {
        return deckRepository.findById(deckId);
    }

    public void deleteDeck(Long deckId) {
        deckRepository.deleteById(deckId);
    }

    public List<Deck> getDecksByUserId(Long userId) {
        return deckRepository.findByUserId(userId);
    }
}
