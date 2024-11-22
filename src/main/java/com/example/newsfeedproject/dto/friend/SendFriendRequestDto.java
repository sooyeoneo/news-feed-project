package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class SendFriendRequestDto {
    private final Long toUserId;

    public SendFriendRequestDto(Long toUserId){
        this.toUserId = toUserId;
    }
}
