package ru.nihongo.study.controller.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nihongo.study.controller.v1.dto.card.UserCardDto;
import ru.nihongo.study.entity.UserCard;

@Mapper(componentModel = "spring")
public interface UserCardMapper {
    @Mapping(target = "front", source = "card.front")
    @Mapping(target = "back", source = "card.back")
    @Mapping(target = "hint", source = "card.hint")
    @Mapping(target = "cardId", source = "card.id")
    UserCardDto mapToDto(UserCard userCard);
}