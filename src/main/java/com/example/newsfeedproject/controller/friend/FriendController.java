package com.example.newsfeedproject.controller.friend;

import com.example.newsfeedproject.dto.friend.*;
import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.service.friend.FriendService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/friends")
public class FriendController {

    private final FriendService friendService;

    @PostMapping
    public ResponseEntity<SendFriendResponseDto> sendFriend(@RequestBody SendFriendRequestDto sendFriendRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        SendFriendResponseDto responseDto = friendService.sendFriend(loginUser.getUserId(), sendFriendRequestDto.getToUserId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @PostMapping("/accept")
    public ResponseEntity<Void> acceptFriend(@RequestBody AcceptFriendRequestDto acceptFriendRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        friendService.acceptFriend(loginUser.getUserId(), acceptFriendRequestDto.getFromUserId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(@RequestBody DeleteFriendRequestDto deleteFriendRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        friendService.deleteFriend(loginUser.getUserId(), deleteFriendRequestDto.getToUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
