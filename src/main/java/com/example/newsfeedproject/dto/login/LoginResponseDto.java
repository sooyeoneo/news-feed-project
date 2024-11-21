package com.example.newsfeedproject.dto.login;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String userName;
    private final Long userId;

    public LoginResponseDto(String userName, Long userId) {
        this.userName = userName;
        this.userId = userId;
    }
}
