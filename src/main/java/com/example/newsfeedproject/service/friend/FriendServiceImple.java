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

@Service
@RequiredArgsConstructor
public class FriendServiceImple implements FriendService{

    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    // 친구 요청, 중복된 친구 요청은 허용하지 않음
    @Override
    public SendFriendResponseDto sendFriend(Long userId, Long toUserId) {

        // 이미 친구 관계라면 예외 발생
        if (friendRepository.existsByFromUserIdAndToUserIdAndAreWeFriend(userId, toUserId, true)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"이미 사용자와 친구 관계입니다");
        }

        // 요청하는 사용자와 요청받는 사용자의 유효성 확인
        User fromUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "FromUser not found"));
        User toUser = userRepository.findById(toUserId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ToUser not found"));

        // 친구 요청 생성 (정방향과 역방향)
        Friend friendRequest = new Friend(fromUser, toUser, false);  // 요청 보내는 사람 -> 받는 사람
        Friend reverseRequest = new Friend(toUser, fromUser, false); // 요청 받는 사람 -> 보내는 사람 (역방향)

        friendRepository.save(friendRequest); // 정방향 저장
        friendRepository.save(reverseRequest); // 역방향 저장

        return new SendFriendResponseDto(toUserId, userId);
    }

    // 친구 요청 수락, 요청을 수락하면 정방향, 역방향 모두 areWeFriend를 true로 변경
    @Override
    public void acceptFriend(Long userId, Long fromUserId) {

        // 친구 요청 데이터 찾기
        // 요청 보내는 사람 -> 받는 사람을 DB에서 찾아 friend에 저장
        Friend friend = friendRepository.findByFromUserIdAndToUserIdAndAreWeFriend(fromUserId,userId,false);

        // 요청 받는 사람 -> 보내는 사람 DB에서 찾아 reversFriend에 저장 (역방향)
        Friend reverseFriend = friendRepository.findByFromUserIdAndToUserIdAndAreWeFriend(userId, fromUserId, false);

        // 친구 요청이 없으면 예외 발생
        if (friend == null || reverseFriend == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"친구신청이 존재하지 않습니다.");    //존재하지 않을 시 예외처리
        }

        // 친구 요청 수락 처리
        // 정방향, 역방향 둘다 areWeFriend를 true 로 변경
        friend.setAreWeFriend(true);
        reverseFriend.setAreWeFriend(true);
        // 정방향, 역방향 변경점을 저장
        friendRepository.save(friend);
        friendRepository.save(reverseFriend);
    }

    // 친구 삭제, 친구 관계를 정방향, 역방향으로 모두 삭제
    @Override
    public void deleteFriend(Long userId, Long toUserId) {
        Friend friend = friendRepository.findByFromUserIdAndToUserIdAndAreWeFriend(userId,toUserId,true);
        Friend reverseFriend = friendRepository.findByFromUserIdAndToUserIdAndAreWeFriend(toUserId, userId, true);

        // 친구 관계가 없으면 예외 발생
        if (friend == null || reverseFriend == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,"친구가 존재하지 않습니다");    //존재하지 않을 시 예외처리
        }

        // 친구 관계 삭제
        friendRepository.delete(friend);
        friendRepository.delete(reverseFriend);
    }

}
