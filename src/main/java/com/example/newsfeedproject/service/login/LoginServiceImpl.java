package com.example.newsfeedproject.service.login;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 사용자 로그인, 이메일과 비밀번호를 통해 사용자 인증
    @Override
    public LoginResponseDto login(String email, String password) {

        // 이메일을 기반으로 사용자 찾기
        User findUser = userRepository.findUserByEmailOrElseThrow(email);

        // 비밀번호 검증
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀 번호가 일치하지 않습니다.");
        }

        // 인증된 사용자 정보 반환
        return new LoginResponseDto(findUser.getId(),findUser.getUserName());
    }
}
