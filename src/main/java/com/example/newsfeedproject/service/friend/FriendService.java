package com.example.newsfeedproject.service.friend;

import com.example.newsfeedproject.dto.friend.DeleteFriendRequestDto;
import com.example.newsfeedproject.dto.friend.FriendResDto;
import com.example.newsfeedproject.dto.friend.SendFriendRequestDto;
import com.example.newsfeedproject.dto.friend.SendFriendResponseDto;

import java.util.List;

public interface FriendService {
    SendFriendResponseDto sendFriend(SendFriendRequestDto sendFriendRequestDto);
    List<FriendResDto> findAllFriends();
    void deleteFriend(DeleteFriendRequestDto deleteFriendRequestDto);
}
