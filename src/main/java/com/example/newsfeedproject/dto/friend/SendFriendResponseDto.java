package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class SendFriendResponseDto {

    private final Long toUserId;
    private final Long fromUserId;

    public SendFriendResponseDto(Long toUserId, Long fromUserId){
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}
