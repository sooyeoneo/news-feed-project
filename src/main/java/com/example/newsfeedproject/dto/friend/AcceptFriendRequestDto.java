package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class AcceptFriendRequestDto {
    private Long fromUserId;
    private Long toUserId;

    public AcceptFriendRequestDto(Long fromUserId, Long toUserId){
        this.fromUserId = fromUserId;
        this.toUserId = toUserId;
    }
}
