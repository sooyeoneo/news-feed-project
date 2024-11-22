package com.example.newsfeedproject.repository;

import com.example.newsfeedproject.entity.like.Like;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

    boolean existsByUserIdAndPostId(Long userId, Long postId);
    boolean existsByUserIdAndCommentId(Long userId, Long commentId);
    void deleteByUserIdAndPostId(Long userId, Long postId);
    void deleteByUserIdAndCommentId(Long userId, Long commentId);
}
