package ru.nihongo.study.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nihongo.study.controller.v1.dto.card.CreateCardDto;
import ru.nihongo.study.controller.v1.dto.card.CardDto;
import ru.nihongo.study.controller.v1.dto.card.ReviewCountDto;
import ru.nihongo.study.controller.v1.dto.card.UserCardDto;
import ru.nihongo.study.controller.v1.mappers.CardMapper;
import ru.nihongo.study.controller.v1.mappers.UserCardMapper;
import ru.nihongo.study.entity.Card;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.service.CardService;
import ru.nihongo.study.service.DeckService;
import ru.nihongo.study.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final CardMapper cardMapper;
    private final ReviewService reviewService;
    private final UserCardMapper userCardMapper;
    private final DeckService deckService;

    // Общие методы над карточками

    @PostMapping
    public ResponseEntity<CardDto> createCard(@RequestBody CreateCardDto createCardDto) {
        Card card = cardMapper.mapToEntity(createCardDto);
        Card createdCard = cardService.createCard(card);
        CardDto responseDTO = cardMapper.mapToDto(createdCard);
        return ResponseEntity.ok(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/deck/{deckId}")
    public ResponseEntity<List<CardDto>> getCardsByDeckId(@PathVariable Long deckId) {
        List<Card> cards = cardService.getCardsByDeckId(deckId);
        List<CardDto> cardDTOs = cards.stream()
            .map(cardMapper::mapToDto)
            .toList();
        return ResponseEntity.ok(cardDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CardDto> getCardById(@PathVariable Long id) {
        Card card = cardService.getCardById(id);
        return ResponseEntity.ok(cardMapper.mapToDto(card));
    }

    //Специальные методы с учетом юзера

    @GetMapping("/deck/{deckId}/review-count")
    public ResponseEntity<ReviewCountDto> getReviewCount(@PathVariable Long deckId) {
        long count = cardService.getCardsForReviewCount(deckId);
        return ResponseEntity.ok(new ReviewCountDto(count));
    }

    @GetMapping("/deck/{deckId}/user")
    public ResponseEntity<List<UserCardDto>> getUserCardsByDeckId(@PathVariable Long deckId) {
        // Проверяем, нужна ли инициализация карточек
        if (deckService.needsCardInitialization(deckId)) {
            reviewService.initializeCardsForUser(deckId);
        }

        List<UserCard> userCards = cardService.getUserCardsByDeckId(deckId);
        List<UserCardDto> userCardDTOs = userCards.stream()
            .map(userCardMapper::mapToDto)
            .toList();
        return ResponseEntity.ok(userCardDTOs);
    }
}