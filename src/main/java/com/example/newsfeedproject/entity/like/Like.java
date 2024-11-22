package com.example.newsfeedproject.entity.like;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long postId;
    private Long commentId;


    public Like(){}

    public Like postLike(Long userId, Long postId) {
        this.postId = postId;
        this.userId = userId;
        return this;
    }

    public Like commentLike(Long userId, Long commentId) {
        this.commentId = commentId;
        this.userId = userId;
        return this;
    }
}
