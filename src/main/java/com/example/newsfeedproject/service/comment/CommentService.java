package com.example.newsfeedproject.service.comment;

import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import org.springframework.data.domain.Page;

public interface CommentService {

    CommentResponseDto createComment(Long postID, String userName, String comment);

    Page<CommentResponseDto> findAllComment(Long postId, int page, int size);

    CommentResponseDto updateComment(Long id, String comment);

    void deleteComment(Long id);
}
