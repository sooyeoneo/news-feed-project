package com.example.newsfeedproject.repository.comment;

import com.example.newsfeedproject.entity.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    default Comment findCommentByCommentIdOrElseThrow(Long id) {
        return findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글이 존재하지 않습니다."));
    }

    Page<Comment> findCommentsByPostId(Long postId, Pageable pageable);
}
