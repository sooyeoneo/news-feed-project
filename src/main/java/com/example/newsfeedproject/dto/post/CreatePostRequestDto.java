package com.example.newsfeedproject.dto.post;

import lombok.Getter;

@Getter
public class CreatePostRequestDto {

    private final String title;

    private final String contents;

    public CreatePostRequestDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
