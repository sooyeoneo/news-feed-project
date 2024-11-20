package com.example.newsfeedproject.dto.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SignUpResponseDto {

    private final Long id;

    private final String userName;

    private final String email;


    private final String age;

    private final LocalDateTime createTime;

    public SignUpResponseDto(Long id, String userName, String email, String age, LocalDateTime createTime) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.age = age;
        this.createTime = createTime;
    }
}
