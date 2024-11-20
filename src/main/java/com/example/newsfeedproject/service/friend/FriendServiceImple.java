package com.example.newsfeedproject.service.friend;

import com.example.newsfeedproject.dto.friend.DeleteFriendRequestDto;
import com.example.newsfeedproject.dto.friend.FriendResDto;
import com.example.newsfeedproject.dto.friend.SendFriendRequestDto;
import com.example.newsfeedproject.dto.friend.SendFriendResponseDto;
import com.example.newsfeedproject.entity.friend.Friend;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.friend.FriendRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FriendServiceImple implements FriendService{

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    @Override
    public SendFriendResponseDto sendFriend(SendFriendRequestDto dto) {

        if (friendRepository.existsByFromUserIdAndToUserIdAndAreWeFriend(dto.getFromUserId(), dto.getToUserId(), true)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 사용자와 친구 관계입니다");
        }

        User fromUser = userRepository.findById(dto.getFromUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FromUser not found"));
        User toUser = userRepository.findById(dto.getToUserId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToUser not found"));

        Friend friendRequest = new Friend(fromUser, toUser, false);  // 요청 보내는 사람 -> 받는 사람
        Friend reverseRequest = new Friend(toUser, fromUser, false); // 요청 받는 사람 -> 보내는 사람 (역방향)

        friendRepository.save(friendRequest);
        friendRepository.save(reverseRequest);

        return new SendFriendResponseDto(dto.getToUserId(), dto.getFromUserId());
    }

    @Override
    public List<FriendResDto> findAllFriends() {
        return List.of();
    }

    @Override
    public void deleteFriend(DeleteFriendRequestDto deleteFriendRequestDto) {

    }
}
