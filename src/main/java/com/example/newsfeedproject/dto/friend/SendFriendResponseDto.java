package com.example.newsfeedproject.dto.friend;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class SendFriendResponseDto {

    @JsonProperty("to_user_id")
    private final Long toUserId;

    @JsonProperty("from_user_id")
    private final Long fromUserId;

    public SendFriendResponseDto(Long toUserId, Long fromUserId){
        this.toUserId = toUserId;
        this.fromUserId = fromUserId;
    }
}
