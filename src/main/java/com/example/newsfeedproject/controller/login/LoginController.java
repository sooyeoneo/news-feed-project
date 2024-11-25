package com.example.newsfeedproject.controller.login;

import com.example.newsfeedproject.dto.login.LoginRequestDto;
import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.service.login.LoginService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    // 사용자 로그인
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto, // 로그인 요청 데이터
            HttpServletRequest servletRequest // HTTP 요청 객체
    ) {
        // 로그인 서비스 호출
        LoginResponseDto loginResponseDto = loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // 세션 생성 및 사용자 정보 저장
        HttpSession httpSession = servletRequest.getSession();
        httpSession.setAttribute("LOGIN_USER", loginResponseDto);

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK); // 로그인 성공 결과 반환
    }

    // 사용자 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {
        HttpSession httpSession = servletRequest.getSession(false); // 현재 세션 조회
        if (httpSession != null) {
            httpSession.invalidate(); // 세션 무효화

        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 로그아웃 성공 반환
    }
}
