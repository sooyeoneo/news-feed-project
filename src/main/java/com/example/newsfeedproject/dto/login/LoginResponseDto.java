package com.example.newsfeedproject.dto.login;

import lombok.Getter;

@Getter
public class LoginResponseDto {

    private String userName;

    public LoginResponseDto(String userName) {
        this.userName = userName;
    }
}