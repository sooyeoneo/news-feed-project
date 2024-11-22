package com.example.newsfeedproject.dto.login;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final Long userId;
    private final String userName;

    public LoginResponseDto(Long userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }
}
