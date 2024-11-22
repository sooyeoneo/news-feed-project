package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class DeleteFriendRequestDto {
    private Long toUserId;

    public DeleteFriendRequestDto(Long toUserid){
        this.toUserId = toUserid;
    }
}
