package com.example.newsfeedproject.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final String userName;

    private final String comment;

    public CommentRequestDto(String userName, String comment) {
        this.userName = userName;
        this.comment = comment;
    }
}
