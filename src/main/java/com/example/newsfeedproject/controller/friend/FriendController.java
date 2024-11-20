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

    @PostMapping
    public ResponseEntity<SendFriendResponseDto> sendFriend(@RequestBody SendFriendRequestDto sendFriendRequestDto) {
        // Service 호출하여 친구 요청 처리
        SendFriendResponseDto responseDto = friendService.sendFriend(sendFriendRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);  // 성공적으로 처리된 경우 201 응답
    }

    @GetMapping
    public ResponseEntity<List<FriendResDto>> findAllFriends(){
        return new ResponseEntity<>(friendService.findAllFriends(),HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(@RequestBody DeleteFriendRequestDto deleteFriendRequestDto){
        friendService.deleteFriend(deleteFriendRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
