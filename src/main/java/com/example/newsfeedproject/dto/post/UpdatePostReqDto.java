package com.example.newsfeedproject.dto.post;

import lombok.Getter;

@Getter
public class UpdatePostReqDto {

    private final String title;

    private final String contents;

    public UpdatePostReqDto(String title, String contents) {
        this.title = title;
        this.contents = contents;
    }
}
