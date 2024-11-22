package com.example.newsfeedproject.dto.post;

import lombok.Getter;

@Getter
public class FriendPostsReqestDto {
    private final Long friendId;

    public FriendPostsReqestDto(Long friendId){
        this.friendId = friendId;
    }
}
