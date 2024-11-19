package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class FriendReqDto {

    private final Long userId;

    public FriendReqDto(Long userId) {
        this.userId = userId;
    }
}
