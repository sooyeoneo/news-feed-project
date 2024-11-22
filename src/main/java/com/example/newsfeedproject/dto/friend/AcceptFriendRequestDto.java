package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class AcceptFriendRequestDto {
    private final Long fromUserId;

    public AcceptFriendRequestDto(Long fromUserId){
        this.fromUserId = fromUserId;
    }
}
