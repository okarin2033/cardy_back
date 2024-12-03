package ru.nihongo.study.adapter.controller.v1.mappers;

import org.mapstruct.Mapper;
import ru.nihongo.study.adapter.controller.v1.dto.UserDto;
import ru.nihongo.study.entity.UserInfo;

@Mapper(componentModel = "spring")
public interface UserDtoMapper {
    UserDto mapToDto(UserInfo userInfo);
}
