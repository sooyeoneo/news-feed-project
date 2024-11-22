package com.example.newsfeedproject.service.friend;

import com.example.newsfeedproject.dto.friend.*;

import java.util.List;

public interface FriendService {
    SendFriendResponseDto sendFriend(Long userId, Long toUserId);
    void deleteFriend(Long userId, Long toUserId);
    void acceptFriend(Long userId, Long fromUserId);
}
