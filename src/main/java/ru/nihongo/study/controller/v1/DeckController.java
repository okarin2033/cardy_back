package ru.nihongo.study.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.service.DeckService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/decks")
public class DeckController {

    @Autowired
    private DeckService deckService;

    @PostMapping
    public ResponseEntity<Deck> createDeck(@RequestHeader("User-ID") Long userId, @RequestBody Deck deck) {
        deck.getUserInfos().add(new UserInfo(userId));
        Deck createdDeck = deckService.createDeck(deck);
        return ResponseEntity.ok(createdDeck);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Deck> getDeckById(@PathVariable Long id) {
        Optional<Deck> deck = deckService.getDeckById(id);
        return deck.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Deck>> getDecksByUserId(@RequestHeader("User-ID") Long userId) {
        List<Deck> decks = deckService.getDecksByUserId(userId);
        return ResponseEntity.ok(decks);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeck(@PathVariable Long id) {
        deckService.deleteDeck(id);
        return ResponseEntity.noContent().build();
    }
}
