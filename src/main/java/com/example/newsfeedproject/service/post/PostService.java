package com.example.newsfeedproject.service.post;

import com.example.newsfeedproject.dto.post.PostResponseDto;
import org.springframework.data.domain.Page;

public interface PostService {

    PostResponseDto createPost(String userName, String title, String contents);

    Page<PostResponseDto> findAllPost(int page, int size);

    PostResponseDto updatePost(Long id, String title, String contents);

    void deletePost(Long id);

    Page<PostResponseDto> findFriendPost(int page, int size, Long userId, Long friendId);

    void likePost(Long userId, Long postId);
}
