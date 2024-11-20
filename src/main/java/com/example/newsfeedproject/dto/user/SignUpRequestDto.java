package com.example.newsfeedproject.dto.user;

import lombok.Getter;

@Getter
public class SignUpRequestDto {

    private final String userName;

    private final String email;

    private final String password;

    private final String age;

    public SignUpRequestDto(String userName, String email, String password, String age) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.age = age;
    }
}
