package ru.nihongo.study.controller.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nihongo.study.controller.v1.dto.card.CreateCardDto;
import ru.nihongo.study.controller.v1.dto.card.CardDto;
import ru.nihongo.study.entity.Card;

@Mapper(componentModel = "spring")
public interface CardMapper {
    @Mapping(target = "deckId", source = "deck.id")
    CardDto mapToDto(Card card);
    @Mapping(source = "deckId", target = "deck.id")
    Card mapToEntity(CreateCardDto dto);
}