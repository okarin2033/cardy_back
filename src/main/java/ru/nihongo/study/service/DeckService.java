package ru.nihongo.study.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.repository.DeckRepository;
import ru.nihongo.study.repository.UserInfoRepository;
import ru.nihongo.study.repository.exception.EntityNotFoundException;
import ru.nihongo.study.service.utils.FSRSCalculator;
import ru.nihongo.study.service.utils.SecurityUtil;
import ru.nihongo.study.repository.UserCardRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class DeckService {
    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private FSRSCalculator fsrsCalculator;
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Autowired
    private UserCardRepository userCardRepository;

    public Deck createDeck(Deck deck) {
        deck.setUserInfos(List.of(SecurityUtil.getcurrentUserInfo()));
        return deckRepository.save(deck);
    }

    public Deck getDeckById(Long deckId) {
        return deckRepository.findById(deckId).orElseThrow(() -> new EntityNotFoundException("DockService", deckId));
    }

    public void deleteDeck(Long deckId) {
        deckRepository.deleteById(deckId);
    }

    public List<Deck> getUserDecks() {
        return deckRepository.findByUserInfosContaining(SecurityUtil.getcurrentUserInfo()).stream()
            .peek(deck -> deck.setNeedReview(userCardRepository.countCardsToReview(LocalDateTime.now(), deck.getId(),
                SecurityUtil.getcurrentUserInfo(), Boolean.FALSE)))
            .toList();
    }

    public boolean needsCardInitialization(Long deckId) {
        UserInfo user = SecurityUtil.getcurrentUserInfo();
        Deck deck = deckRepository.findById(deckId)
            .orElseThrow(() -> new RuntimeException("Deck not found"));
        
        // Получаем количество карточек в колоде
        long totalCards = deck.getCards().size();
        
        // Получаем количество инициализированных карточек пользователя
        long initializedCards = userCardRepository.countByUserAndCardDeckId(user, deckId);
        
        // Если количество инициализированных карточек меньше общего количества,
        // значит нужна инициализация
        return initializedCards < totalCards;
    }
}
