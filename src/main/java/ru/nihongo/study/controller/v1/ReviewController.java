package ru.nihongo.study.controller.v1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.controller.v1.dto.ReviewActionDto;
import ru.nihongo.study.controller.v1.dto.UserCardDto;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.enumeration.ReviewAction;
import ru.nihongo.study.service.ReviewService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @GetMapping("/cards")
    public ResponseEntity<List<UserCardDto>> getCardsForReview(
        @RequestParam Long deckId,
        @RequestParam Long userId) {
        List<UserCard> userCards = reviewService.getCardsForReview(deckId, userId);
        List<UserCardDto> userCardDTOs = userCards.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
        return ResponseEntity.ok(userCardDTOs);
    }

    @PostMapping("/cards")
    public ResponseEntity<Void> reviewCard(
        @RequestParam Long userId,
        @RequestBody ReviewActionDto reviewActionDTO) {
        ReviewAction action;
        try {
            action = ReviewAction.valueOf(reviewActionDTO.getAction().name().toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
        reviewService.updateCardReview(userId, reviewActionDTO.getUserCardId(), action);
        return ResponseEntity.ok().build();
    }

    private UserCardDto convertToDTO(UserCard userCard) {
        UserCardDto dto = new UserCardDto();
        dto.setCardId(userCard.getCard().getId());
        dto.setFront(userCard.getCard().getFront());
        dto.setBack(userCard.getCard().getBack());
        dto.setHint(userCard.getCard().getHint());
        dto.setNew(userCard.isNew());
        return dto;
    }
}