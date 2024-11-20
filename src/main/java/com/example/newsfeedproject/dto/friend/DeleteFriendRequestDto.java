package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonCreator;

public class DeleteFriendRequestDto {
    private String username;

    @JsonCreator
    public DeleteFriendRequestDto(String username){
        this.username = username;
    }
}
