package com.example.newsfeedproject.service.user;

import com.example.newsfeedproject.dto.user.SignUpResponseDto;
import com.example.newsfeedproject.dto.user.UserResponseDto;

import java.util.List;

public interface UserService {

    SignUpResponseDto signup(String username, String email, String password, String age);

    List<UserResponseDto> findAllUsers();

    void updatePassword(Long id, String oldPassword, String newPassword);

    void deleteUser(Long id, String password);
}
