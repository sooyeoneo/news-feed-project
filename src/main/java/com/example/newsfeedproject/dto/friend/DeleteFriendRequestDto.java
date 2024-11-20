package com.example.newsfeedproject.dto.friend;

import lombok.Getter;

@Getter
public class DeleteFriendRequestDto {
    private Long fromUserId;
    private Long toUserId;

    public DeleteFriendRequestDto(Long fromUserid, Long toUserid){
        this.fromUserId = fromUserid;
        this.toUserId = toUserid;
    }
}
