package com.example.newsfeedproject.controller.user;

import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.dto.user.DeleteUserRequestDto;
import com.example.newsfeedproject.dto.user.SignUpRequestDto;
import com.example.newsfeedproject.dto.user.SignUpResponseDto;
import com.example.newsfeedproject.dto.user.UpdatePasswordRequestDto;
import com.example.newsfeedproject.dto.user.UserResponseDto;
import com.example.newsfeedproject.service.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signup(@RequestBody SignUpRequestDto dto) {
        // 회원 가입 서비스 호출
        SignUpResponseDto signUpResponseDto =
                userService.signup(
                        dto.getUserName(),
                        dto.getEmail(),
                        dto.getPassword(),
                        dto.getAge()
                );

        return new ResponseEntity<>(signUpResponseDto, HttpStatus.CREATED); // 가입 결과 반환
    }

    // 프로필 전체 조회
    @GetMapping
    public List<UserResponseDto> findAllUsers() {

        return userService.findAllUsers(); // 사용자 목록 반환
    }

    // 비밀번호 변경
    @PatchMapping("{user_id}")
    public ResponseEntity<Void> updatePassword(@RequestBody UpdatePasswordRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션 가져옴
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER"); // 로그인된 사용자 정보 조회

        // 비밀번호 변경 서비스 호출
        userService.updatePassword(loginUser.getUserId(), dto.getOldPassword(), dto.getNewPassword());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 프로필 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteUser(@RequestBody DeleteUserRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 사용자 삭제 서비스 호출
        userService.deleteUser(loginUser.getUserId(), dto.getPassword());
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate(); // 세션 무효화
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
