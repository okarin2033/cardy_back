package ru.nihongo.study.service;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.repository.DeckRepository;
import ru.nihongo.study.repository.UserInfoRepository;
import ru.nihongo.study.repository.exception.EntityNotFoundException;
import ru.nihongo.study.service.utils.FSRSCalculator;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private FSRSCalculator fsrsCalculator;
    @Autowired
    private UserInfoRepository userInfoRepository;

    public Deck createDeck(Deck deck) {
        return deckRepository.save(deck);
    }

    public Deck getDeckById(Long deckId) {
        return deckRepository.findById(deckId).orElseThrow(() -> new EntityNotFoundException("DockService", deckId));
    }

    public void deleteDeck(Long deckId) {
        deckRepository.deleteById(deckId);
    }

    public List<Deck> getDecksByUserId(Long userId) {
        return deckRepository.findByUserInfosContaining(new UserInfo(userId));
    }
}
