package com.example.newsfeedproject.service.login;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 유저 로그인
    @Override
    public LoginResponseDto login(String email, String password) {
        User findUser = userRepository.findUserByEmailOrElseThrow(email);
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀 번호가 일치하지 않습니다.");
        }
        return new LoginResponseDto(findUser.getId(),findUser.getUserName());
    }
}
