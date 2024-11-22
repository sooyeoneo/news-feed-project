package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class SendFriendRequestDto {
    private Long toUserId;

    public SendFriendRequestDto(Long toUserId){
        this.toUserId = toUserId;
    }
}
