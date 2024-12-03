package ru.nihongo.study.adapter.controller.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nihongo.study.adapter.controller.v1.dto.card.ReviewCardDto;
import ru.nihongo.study.adapter.controller.v1.dto.card.UserCardDto;
import ru.nihongo.study.entity.UserCard;
import ru.nihongo.study.entity.model.ReviewCard;

@Mapper(componentModel = "spring")
public interface UserCardMapper {
    @Mapping(target = "front", source = "card.front")
    @Mapping(target = "back", source = "card.back")
    @Mapping(target = "hint", source = "card.hint")
    @Mapping(target = "cardId", source = "card.id")
    @Mapping(target = "stability", source = "stability")
    @Mapping(target = "difficulty", source = "difficulty")
    @Mapping(target = "lastReviewed", source = "lastReviewed")
    @Mapping(target = "nextReview", source = "nextReview")
    UserCardDto mapToDto(UserCard userCard);

    ReviewCardDto mapToReviewDto(ReviewCard userCard);
}
