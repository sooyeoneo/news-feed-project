package com.example.newsfeedproject.dto.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponseDto {

    private final Long userId;

    private final String userName;

    private final String email;

    private final String age;

    private final LocalDateTime createTime;

    public SignUpResponseDto(Long id, String userName, String email, String age, LocalDateTime createTime) {
        this.userId = id;
        this.userName = userName;
        this.email = email;
        this.age = age;
        this.createTime = createTime;
    }
}
