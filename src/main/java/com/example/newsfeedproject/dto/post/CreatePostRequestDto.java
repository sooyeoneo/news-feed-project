package com.example.newsfeedproject.dto.post;

import lombok.Getter;

@Getter
public class CreatePostRequestDto {

    private final String userName;

    private final String title;

    private final String contents;

    public CreatePostRequestDto(String userName, String title, String contents) {
        this.userName = userName;
        this.title = title;
        this.contents = contents;
    }
}
