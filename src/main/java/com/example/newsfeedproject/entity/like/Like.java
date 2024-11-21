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

    public Like(){}

    public Like(Long userId, Long postId){
        this.userId = userId;
        this.postId = postId;
    }
}
