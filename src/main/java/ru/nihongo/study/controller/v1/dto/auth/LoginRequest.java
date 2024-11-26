package ru.nihongo.study.controller.v1.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}