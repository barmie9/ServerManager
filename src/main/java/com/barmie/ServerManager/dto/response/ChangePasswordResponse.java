package com.barmie.ServerManager.dto.response;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChangePasswordResponse {
    private final String message;
    private final LocalDateTime timestamp;

    public ChangePasswordResponse(String message) {
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}