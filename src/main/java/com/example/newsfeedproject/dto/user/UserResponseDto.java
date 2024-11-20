package com.example.newsfeedproject.dto.user;

import com.example.newsfeedproject.entity.user.User;
import lombok.Getter;

@Getter
public class UserResponseDto {

    private final Long id;

    private final String userName;

    private final String email;

    private final String age;

    public UserResponseDto(Long id, String userName, String email, String age) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.age = age;
    }

    public static UserResponseDto toDto(User user) {
        return new UserResponseDto(user.getId(), user.getUserName(), user.getEmail(), user.getAge());
    }
}
