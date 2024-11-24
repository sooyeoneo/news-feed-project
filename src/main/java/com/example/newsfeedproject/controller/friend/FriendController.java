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

    // 친구 요청
    @PostMapping
    public ResponseEntity<SendFriendResponseDto> sendFriend(@RequestBody SendFriendRequestDto sendFriendRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션 가져옴
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER"); // 로그인된 사용자 정보 조회

        // 친구 요청 서비스 호출
        SendFriendResponseDto responseDto = friendService.sendFriend(loginUser.getUserId(), sendFriendRequestDto.getToUserId());
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED); // 친구 요청 결과 반환
    }

    // 찬구 요청 수락
    @PostMapping("/accept")
    public ResponseEntity<Void> acceptFriend(@RequestBody AcceptFriendRequestDto acceptFriendRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 친구 요청 수락 서비스 호출
        friendService.acceptFriend(loginUser.getUserId(), acceptFriendRequestDto.getFromUserId());
        return new ResponseEntity<>(HttpStatus.ACCEPTED);  // 친구 수락 상태 반환
    }

    // 친구 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteFriend(@RequestBody DeleteFriendRequestDto deleteFriendRequestDto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 친구 삭제 서비스 호출
        friendService.deleteFriend(loginUser.getUserId(), deleteFriendRequestDto.getToUserId());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
