package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class AcceptFriendRequestDto {
    @JsonProperty("from_user_id")
    private Long fromUserId;

    public AcceptFriendRequestDto () {}
    public AcceptFriendRequestDto(Long fromUserId){
        this.fromUserId = fromUserId;
    }
}
