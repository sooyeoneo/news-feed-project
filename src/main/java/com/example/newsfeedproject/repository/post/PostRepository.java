package com.example.newsfeedproject.repository.post;

import com.example.newsfeedproject.entity.post.Post;
import com.example.newsfeedproject.entity.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface PostRepository extends JpaRepository<Post, Long> {

    default Post findPostByIdOrElseThrow(long id) {
        return findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "피드가 존재하지 않습니다."));
    }

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByUser(User user, Pageable pageable);
}
