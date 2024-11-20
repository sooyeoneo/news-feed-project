package com.example.newsfeedproject.service.post;

import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.entity.post.Post;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.post.PostRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;

    //피드 생성
    public PostResponseDto createPost(String userName, String title, String contents) {

        User user = userRepository.findUserByUserNameOrElseThrow(userName);

        Post post = new Post(title, contents);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getCreateTime(), post.getUpdateTime());
    }

    //피드 조회
    public Page<PostResponseDto> findAllPost(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(post -> new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getCreateTime(), post.getUpdateTime()));
    }

    //피드 수정
    @Transactional
    public PostResponseDto updatePost(Long id, String title, String contents) {

        Post post = postRepository.findPostByIdOrElseThrow(id);
        post.updatePost(title, contents);

        return new PostResponseDto(post.getId(), post.getTitle(), post.getContents(), post.getCreateTime(), post.getUpdateTime());
    }

    //피드 삭제
    public void deletePost(Long id) {

        Post post = postRepository.findPostByIdOrElseThrow(id);

        postRepository.delete(post);
    }
}
