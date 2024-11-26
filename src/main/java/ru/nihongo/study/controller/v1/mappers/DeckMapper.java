package ru.nihongo.study.controller.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nihongo.study.controller.v1.dto.deck.CreateDeckDto;
import ru.nihongo.study.controller.v1.dto.deck.DeckDto;
import ru.nihongo.study.entity.Deck;
import ru.nihongo.study.entity.UserInfo;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DeckMapper {
    @Mapping(target = "count", expression = "java(getCount(deck))")
    DeckDto mapToDto(Deck deck);

    @Mapping(source = "userId", target = "userInfos")
    Deck mapToEntity(CreateDeckDto dto);

    default List<UserInfo> mapUserIdToUserInfos(Long userId) {
        if (userId == null) {
            return new ArrayList<>();
        }
        List<UserInfo> userInfos = new ArrayList<>();
        userInfos.add(new UserInfo(userId));
        return userInfos;
    }

    default Long getCount(Deck deck) {
        return (long) deck.getCards().size();
    }
}