package com.example.newsfeedproject.service.login;

import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.dto.user.UserResponseDto;

public interface LoginService {

    LoginResponseDto login(String email, String password);
}
