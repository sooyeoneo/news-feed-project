package com.example.newsfeedproject.service.login;

import com.example.newsfeedproject.config.PasswordEncoder;
import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.login.LoginRepository;
import com.example.newsfeedproject.repository.post.PostRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final LoginRepository loginRepository;
    private final PasswordEncoder passwordEncoder;

    public LoginResponseDto findUserByEmail(String email) {

        Optional<User> optionalUser = loginRepository.findUserByEmail(email);
        if (optionalUser.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "존재하지 않는 email 입니다. ");
        }
        User findUser = optionalUser.get();
        return new LoginResponseDto(findUser.getUserName());
    }

    // 비밀번호 일치 여부 확인
    @Transactional
    public void matchPassword(String email, String password) {

        User user = loginRepository.findUserByEmailOrElseThrow(email);
        if (!user.getPassword().equals(password)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀 번호가 일치하지 않습니다.");
        }
    }

    // 유저 로그인
    @Override
    public LoginResponseDto login(String email, String password) {
        User findUser = loginRepository.findUserByEmailOrElseThrow(email);
        if (!passwordEncoder.matches(password, findUser.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "비밀 번호가 일치하지 않습니다.");
        }
        return new LoginResponseDto(findUser.getEmail());
    }
}
