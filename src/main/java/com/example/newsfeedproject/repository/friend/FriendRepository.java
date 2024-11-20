package com.example.newsfeedproject.repository.friend;

import com.example.newsfeedproject.entity.friend.Friend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByFromUserIdAndToUserIdAndAreWeFriend(Long fromUserId, Long toUserId, boolean b);
    Friend findByFromUserIdAndToUserIdAndAreWeFriend(Long fromUserId, Long toUserId, boolean b);
}
