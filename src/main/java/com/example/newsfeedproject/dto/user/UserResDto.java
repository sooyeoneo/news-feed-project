package com.example.newsfeedproject.dto.user;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class UserResDto {

    private final Long id;

    private final String userName;

    private final String email;

    private final LocalDateTime createTime;

    private final LocalDateTime updateTime;


    public UserResDto(Long id, String userName, String email, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
