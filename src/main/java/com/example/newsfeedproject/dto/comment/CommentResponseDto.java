package com.example.newsfeedproject.dto.comment;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    //댓글 id
    private final Long comment_Id;

    //댓글 작성자
    private final String writer;

    //댓글 내용
    private final String comment;
  
    //댓글 좋아요 갯수
    private final int commentLike;
  
    private final LocalDateTime createTime;

    @Setter
    private LocalDateTime updateTime;

    public CommentResponseDto(Long id, String writer, String comment, int commentLike, LocalDateTime createTime, LocalDateTime updatedTime) {
        this.comment_Id = id;
        this.writer = writer;
        this.comment = comment;
        this.commentLike = commentLike;
        this.createTime = createTime;
        this.updatedTime = updatedTime;
    }
}
