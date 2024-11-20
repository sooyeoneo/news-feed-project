package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class SendFriendRequestDto {
    private Long toUserId;
    private Long fromUserId;

    public SendFriendRequestDto(Long toUserId, Long fromUserId){
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}
