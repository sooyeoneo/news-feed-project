package com.example.newsfeedproject.dto.post;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class PostResponseDto {

    private final Long id;

    private final String title;

    private final String contents;

    private final LocalDateTime createTime;

    private final int like;

    @Setter
    private LocalDateTime updateTime;

    public PostResponseDto(Long id, String title, String contents, LocalDateTime createTime, LocalDateTime updateTime, int like) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.like = like;
    }
}
