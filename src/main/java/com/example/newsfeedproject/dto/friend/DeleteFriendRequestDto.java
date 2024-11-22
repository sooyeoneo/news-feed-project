package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class DeleteFriendRequestDto {
    @JsonProperty("to_user_id")
    private Long toUserId;

    public DeleteFriendRequestDto () {}
    public DeleteFriendRequestDto(Long toUserid){
        this.toUserId = toUserid;
    }
}
