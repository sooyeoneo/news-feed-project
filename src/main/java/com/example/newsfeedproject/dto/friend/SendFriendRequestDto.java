package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SendFriendRequestDto {
    @JsonProperty("to_user_id")
    private Long toUserId;

    public SendFriendRequestDto(){}
    public SendFriendRequestDto(Long toUserId){
        this.toUserId = toUserId;
    }
}
