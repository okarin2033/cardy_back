package ru.nihongo.study.adapter.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nihongo.study.adapter.controller.v1.dto.IdResponseDto;
import ru.nihongo.study.adapter.controller.v1.dto.deck.CreateDeckDto;
import ru.nihongo.study.adapter.controller.v1.dto.deck.DeckDto;
import ru.nihongo.study.adapter.controller.v1.mappers.DeckMapper;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.service.DeckService;

import java.util.List;

@RestController
@RequestMapping("/v1/decks")
@RequiredArgsConstructor
public class DeckController {
    private final DeckService deckService;
    private final DeckMapper deckMapper;

    @PostMapping
    public ResponseEntity<IdResponseDto> createDeck(@RequestBody CreateDeckDto deck) {
        Deck createdDeck = deckService.createDeck(deckMapper.mapToEntity(deck));
        return ResponseEntity.ok(new IdResponseDto(createdDeck.getId()));
    }

    @GetMapping()
    public ResponseEntity<List<DeckDto>> getUserDecks() {
        List<Deck> decks = deckService.getUserDecks();
        return ResponseEntity.ok(decks.stream()
            .map(deckMapper::mapToDto)
            .toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeckDto> getDeckById(@PathVariable Long id) {
        Deck deck = deckService.getDeckById(id);
        return ResponseEntity.ok(deckMapper.mapToDto(deck));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
        return ResponseEntity.noContent().build();
    }
}
