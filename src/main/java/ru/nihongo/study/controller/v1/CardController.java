package ru.nihongo.study.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nihongo.study.controller.v1.dto.card.CreateCardDto;
import ru.nihongo.study.controller.v1.dto.card.CardDto;
import ru.nihongo.study.controller.v1.mappers.CardMapper;
import ru.nihongo.study.entity.Card;
import ru.nihongo.study.service.CardService;

import java.util.List;

@RestController
@RequestMapping("/v1/cards")
@RequiredArgsConstructor
public class CardController {
    private final CardService cardService;
    private final CardMapper cardMapper;

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
}