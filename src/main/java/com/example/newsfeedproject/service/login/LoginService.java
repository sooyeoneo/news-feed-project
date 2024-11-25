package com.example.newsfeedproject.service.login;

import com.example.newsfeedproject.dto.login.LoginResponseDto;

public interface LoginService {

    LoginResponseDto login(String email, String password);
}
