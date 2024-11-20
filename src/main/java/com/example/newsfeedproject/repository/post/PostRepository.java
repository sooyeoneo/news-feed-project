package com.example.newsfeedproject.repository.post;

import com.example.newsfeedproject.entity.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
