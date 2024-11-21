package com.example.newsfeedproject.dto.comment;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    //댓글 id
    private Long id;

    //댓글 내용
    private String comment;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    public CommentResponseDto(Long id, String comment, LocalDateTime createdTime, LocalDateTime updatedTime) {
        this.id = id;
        this.comment = comment;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }
}
