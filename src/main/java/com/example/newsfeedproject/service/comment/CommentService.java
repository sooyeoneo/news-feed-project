package com.example.newsfeedproject.service.comment;

import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Page;

public interface CommentService {

    CommentResponseDto createComment(Long postId, Long userId, String comment);

    Page<CommentResponseDto> findAllComment(Long postId, int page, int size);

    CommentResponseDto updateComment(Long userId, Long commentId, String comment);

    void deleteComment(Long userId, Long commentId);
}
