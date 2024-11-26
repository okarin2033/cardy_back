package ru.nihongo.study.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nihongo.study.entity.UserInfo;
import ru.nihongo.study.repository.UserInfoRepository;
import ru.nihongo.study.repository.exception.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final UserInfoRepository userInfoRepository;

    public UserInfo getByUsername(String login) {
        return userInfoRepository.findByUsername(login)
            .orElseThrow(() -> new EntityNotFoundException("UserInfo", login));
    }

    public UserInfo getById(Long id) {
        return userInfoRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("UserInfo", id));
    }
}
