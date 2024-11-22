package com.example.newsfeedproject.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long postId;

    private final String writer;

    private final String title;

    private final String contents;

    private final int like;

    private final LocalDateTime createTime;

    @Setter
    private LocalDateTime updateTime;

    public PostResponseDto(Long id, String writer, String title, String contents, int like, LocalDateTime createTime, LocalDateTime updateTime) {
        this.postId = id;
        this.writer = writer;
        this.title = title;
        this.contents = contents;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.like = like;
    }
}
