package com.example.newsfeedproject.dto.comment;

import lombok.Getter;

@Getter
public class LikeCommentResponseDto {

    private final int countLike;

    public LikeCommentResponseDto(int countLike) {
        this.countLike = countLike;
    }
}
