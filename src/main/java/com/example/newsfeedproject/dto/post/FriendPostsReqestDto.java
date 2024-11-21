package com.example.newsfeedproject.dto.post;

import lombok.Getter;

@Getter
public class FriendPostsReqestDto {
    private final Long userId;
    private final Long friendId;

    public FriendPostsReqestDto(Long userId, Long friendId){
        this.userId = userId;
        this.friendId = friendId;
    }
}
