package com.example.newsfeedproject.dto.post;

import lombok.Getter;

@Getter
public class LikePostResponseDto {
    private int countLike;

    public LikePostResponseDto(int countLike){
        this.countLike = countLike;
    }
}
