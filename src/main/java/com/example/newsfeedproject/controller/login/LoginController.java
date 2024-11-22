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

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
            @Valid @RequestBody LoginRequestDto loginRequestDto,
            HttpServletRequest servletRequest
    ) {
        LoginResponseDto loginResponseDto = loginService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        HttpSession httpSession = servletRequest.getSession();

        httpSession.setAttribute("LOGIN_USER", loginResponseDto);

        return new ResponseEntity<>(loginResponseDto, HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpServletRequest servletRequest) {
        HttpSession httpSession = servletRequest.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
