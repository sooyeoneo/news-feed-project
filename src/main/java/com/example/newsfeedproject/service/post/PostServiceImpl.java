package com.example.newsfeedproject.service.post;

import com.example.newsfeedproject.dto.post.LikePostResponseDto;
import com.example.newsfeedproject.dto.post.PostResponseDto;
import com.example.newsfeedproject.entity.like.Like;
import com.example.newsfeedproject.entity.post.Post;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.LikeRepository;
import com.example.newsfeedproject.repository.friend.FriendRepository;
import com.example.newsfeedproject.repository.post.PostRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService{

    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final FriendRepository friendRepository;
    private final LikeRepository likeRepository;

    //포스트 생성
    public PostResponseDto createPost(String userName, String title, String contents) {

        User user = userRepository.findUserByUserNameOrElseThrow(userName);

        Post post = new Post(title, contents);
        post.setUser(user);

        postRepository.save(post);

        return new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContents(),
                post.getCountLike(),
                post.getCreateTime(),
                post.getUpdateTime());
    }

    //포스트 조회
    public Page<PostResponseDto> findAllPost(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));

        Page<Post> posts = postRepository.findAll(pageable);

        return posts.map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContents(),
                post.getCountLike(),
                post.getCreateTime(),
                post.getUpdateTime()));
    }

    //포스트를 친구조회 (친구인지 확인 후 친구의 게시글 출력)
    @Override
    public Page<PostResponseDto> findFriendPost(int page, int size, Long userId, Long friendId) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createTime"));

        if (!friendRepository.existsByFromUserIdAndToUserId(userId, friendId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "친구 관계가 아닙니다.");
        }

        User friendUser = userRepository.findUserByIdOrElseThrow(friendId);
        Page<Post> friendPosts = postRepository.findByUser(friendUser, pageable);

        return friendPosts.map(post -> new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContents(),
                post.getCountLike(),
                post.getCreateTime(),
                post.getUpdateTime()));
    }

    //좋아요 작업 수행 (이전에 좋아요 하지 않은 사용자라면 좋아요, 이전에 좋아요 했다면 취소)
    @Override
    @Transactional
    public LikePostResponseDto likePost(Long userId, Long postId) {
        boolean already = likeRepository.existsByUserIdAndPostId(userId,postId);
        Post post = postRepository.findPostByIdOrElseThrow(postId);

        //게시물의 작성자는 좋아요를 누를 수 없게 생성
        if(post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "게시물의 작성자는 좋아요를 누를 수 없습니다.");
        }

        if(already) {       //already가 true라면 이미 좋아요를 누른 사용자라고 판단, 좋아요 기록을 삭제하고 좋아요 카운트를 -1
            likeRepository.deleteByUserIdAndPostId(userId, postId);
            post.minusLike();
            postRepository.save(post);
        } else {            //already가 false라면 좋아요를 누르지 않은 사용자이기 때문에 좋아요 기록을 추가하고 카운트 +1
            Like like = new Like(userId,postId);
            likeRepository.save(like);
            post.plusLike();
            postRepository.save(post);
        }

        return new LikePostResponseDto(post.getCountLike());
    }

    //포스트 수정
    @Transactional
    public PostResponseDto updatePost(Long userId, Long id, String title, String contents) {

        Post post = postRepository.findPostByIdOrElseThrow(id);

        //세션값 검증
        if(!post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "작성자만 수정할 수 있습니다.");
        }
        post.updatePost(title, contents);

        return new PostResponseDto(
                post.getId(),
                post.getUser().getUserName(),
                post.getTitle(),
                post.getContents(),
                post.getCountLike(),
                post.getCreateTime(),
                post.getUpdateTime());
    }

    //포스트 삭제
    public void deletePost(Long userId, Long id) {

        Post post = postRepository.findPostByIdOrElseThrow(id);

        //세션값 검증
        if(!post.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "작성자만 삭제할 수 있습니다.");
        }

        postRepository.delete(post);
    }
}
