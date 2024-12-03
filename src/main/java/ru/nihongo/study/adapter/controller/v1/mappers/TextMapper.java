package ru.nihongo.study.adapter.controller.v1.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nihongo.study.adapter.controller.v1.dto.EnumDto;
import ru.nihongo.study.adapter.controller.v1.dto.text.TextListDto;
import ru.nihongo.study.adapter.controller.v1.dto.text.CreateTextDto;
import ru.nihongo.study.adapter.controller.v1.dto.text.TextDto;
import ru.nihongo.study.entity.Text;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.entity.enumeration.Language;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface TextMapper {
    @Mapping(target = "userIds", expression = "java(mapUsersToIds(text.getUsers()))")
    TextDto mapToDto(Text text);

    TextListDto mapToListDto(Text text);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "users", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Text mapToEntity(CreateTextDto dto);

    @Mapping(source = ".", target = "code")
    EnumDto<Language> mapToDto(Language language);

    default List<Long> mapUsersToIds(List<UserInfo> users) {
        if (users == null) return null;
        return users.stream()
                .map(UserInfo::getId)
                .collect(Collectors.toList());
    }
}
