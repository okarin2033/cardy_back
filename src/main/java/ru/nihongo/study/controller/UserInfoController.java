package ru.nihongo.study.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.controller.v1.dto.UserDto;
import ru.nihongo.study.controller.v1.mappers.UserDtoMapper;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.service.UserInfoService;

@RequiredArgsConstructor
@RestController("/v1/user")
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final UserDtoMapper mapper;

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long id) {
        UserInfo user = userInfoService.getById(id);
        return ResponseEntity.ok(mapper.mapToDto(user));
    }
}
