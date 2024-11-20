package com.example.newsfeedproject.service.friend;

import com.example.newsfeedproject.dto.friend.*;

import java.util.List;

public interface FriendService {
    SendFriendResponseDto sendFriend(SendFriendRequestDto sendFriendRequestDto);
    List<FriendResDto> findAllFriends();
    void deleteFriend(DeleteFriendRequestDto deleteFriendRequestDto);
    void acceptFriend(AcceptFriendRequestDto acceptFriendRequestDto);

}
