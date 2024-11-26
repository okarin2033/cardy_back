package ru.nihongo.study.controller.v1;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.controller.v1.dto.ReviewActionDto;
import ru.nihongo.study.controller.v1.dto.card.UserCardDto;
import ru.nihongo.study.controller.v1.mappers.UserCardMapper;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.enumeration.ReviewAction;
import ru.nihongo.study.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/review")
@RequiredArgsConstructor
public class ReviewController {
    private final ReviewService reviewService;
    private final UserCardMapper userCardMapper;

    @GetMapping("/cards")
    public ResponseEntity<List<UserCardDto>> getCardsForReview(
        @RequestParam Long deckId,
        @RequestParam Long userId) {
        List<UserCard> userCards = reviewService.getCardsForReview(deckId, userId);
        List<UserCardDto> userCardDTOs = userCards.stream()
            .map(userCardMapper::mapToDto)
            .collect(Collectors.toList());
        return ResponseEntity.ok(userCardDTOs);
    }

    @PostMapping("/cards")
    public ResponseEntity<Void> reviewCard(
        @RequestBody ReviewActionDto reviewActionDto) {
        ReviewAction action;
        try {
            action = ReviewAction.valueOf(reviewActionDto.getAction().name().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        reviewService.updateCardReview(reviewActionDto.getUserCardId(), action);
        return ResponseEntity.ok().build();
    }
}