package com.example.newsfeedproject.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final String comment;

    public CommentRequestDto(String comment) {
        this.comment = comment;
    }
}
