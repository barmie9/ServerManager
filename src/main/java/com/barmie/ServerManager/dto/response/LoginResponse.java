package com.barmie.ServerManager.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LoginResponse {
    private final String token;
    private final LocalDateTime timestamp;

    public LoginResponse(String token) {
        this.token = token;
        this.timestamp = LocalDateTime.now();
    }
}
