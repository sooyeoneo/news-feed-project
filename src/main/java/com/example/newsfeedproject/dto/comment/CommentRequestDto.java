package com.example.newsfeedproject.dto.comment;

import lombok.Getter;

@Getter
public class CommentRequestDto {

    private String comment;

    public CommentRequestDto() {}

    public CommentRequestDto(String comment) {
        this.comment = comment;
    }
}
