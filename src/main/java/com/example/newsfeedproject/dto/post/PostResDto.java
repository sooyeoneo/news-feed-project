package com.example.newsfeedproject.dto.post;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class PostResDto {

    private final Long id;

    private final String title;

    private final String contents;

    private final Long userId;

    private final LocalDateTime createTime;

    private final LocalDateTime updateTime;

    public PostResDto(Long id, String title, String contents, Long userId, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.userId = userId;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }
}
