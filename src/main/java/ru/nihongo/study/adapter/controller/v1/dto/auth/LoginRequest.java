package ru.nihongo.study.adapter.controller.v1.dto.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}