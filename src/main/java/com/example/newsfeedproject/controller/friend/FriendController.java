package com.example.newsfeedproject.controller.friend;

import com.example.newsfeedproject.dto.friend.*;
import com.example.newsfeedproject.entity.friend.Friend;
import com.example.newsfeedproject.service.friend.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    //로그인 세션 구현시 세션에서 로그인한 유저의 정보를 전달하는 방식으로 변경 예정
    //현재는 dto에서 toUserId를 함께 전달하는 중

    @PostMapping
    public ResponseEntity<SendFriendResponseDto> sendFriend(@RequestBody SendFriendRequestDto sendFriendRequestDto) {
        SendFriendResponseDto responseDto = friendService.sendFriend(sendFriendRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptFriend(@RequestBody AcceptFriendRequestDto acceptFriendRequestDto){
        friendService.acceptFriend(acceptFriendRequestDto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(@RequestBody DeleteFriendRequestDto deleteFriendRequestDto){
        friendService.deleteFriend(deleteFriendRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
