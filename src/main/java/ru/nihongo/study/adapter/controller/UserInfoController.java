package ru.nihongo.study.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nihongo.study.adapter.controller.v1.dto.UserDto;
import ru.nihongo.study.adapter.controller.v1.mappers.UserDtoMapper;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.service.UserInfoService;

@RestController
@RequestMapping("v1/users")
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService userInfoService;
    private final UserDtoMapper mapper;

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("username") String name) {
        UserInfo user = userInfoService.getByUsername(name);
        return ResponseEntity.ok(mapper.mapToDto(user));
    }
}
