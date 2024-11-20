package com.example.newsfeedproject.service.friend;

import com.example.newsfeedproject.dto.friend.*;
import com.example.newsfeedproject.entity.friend.Friend;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.friend.FriendRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

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
    public void acceptFriend(AcceptFriendRequestDto dto) {
        //요청 보내는 사람 -> 받는 사람을 DB에서 찾아 friend에 저장
        Friend friend = friendRepository.findByFromUserIdAndToUserIdAndAreWeFriend(dto.getFromUserId(),dto.getToUserId(),false);
        //요청 받는 사람 -> 보내는 사람 DB에서 찾아 reversFriend에 저장 (역방향)
        Friend reverseFriend = friendRepository.findByFromUserIdAndToUserIdAndAreWeFriend(dto.getToUserId(), dto.getFromUserId(), false);
        if (friend == null || reverseFriend == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"친구신청이 존재하지 않습니다.");    //존재하지 않을 시 예외처리
        }
        //정방향, 역방향 둘다 areWeFriend를 true 로 변경
        friend.setAreWeFriend(true);
        reverseFriend.setAreWeFriend(true);
        //정방향, 역방향 변경점을 저장
        friendRepository.save(friend);
        friendRepository.save(reverseFriend);
    }

    @Override
    public List<FriendResDto> findAllFriends() {
        return List.of();
    }

    @Override
    public void deleteFriend(DeleteFriendRequestDto deleteFriendRequestDto) {

    }

}
