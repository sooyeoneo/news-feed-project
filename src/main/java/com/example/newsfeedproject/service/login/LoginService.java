package com.example.newsfeedproject.service.login;

import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.dto.user.UserResponseDto;

public interface LoginService {

    LoginResponseDto findUserByEmail(String email);
    LoginResponseDto login(String email, String password);
}
