package com.example.newsfeedproject.service.comment;

import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.entity.comment.Comment;
import com.example.newsfeedproject.entity.like.Like;
import com.example.newsfeedproject.entity.post.Post;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.LikeRepository;
import com.example.newsfeedproject.repository.comment.CommentRepository;
import com.example.newsfeedproject.repository.post.PostRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final LikeRepository likeRepository;

    //댓글 생성
    public CommentResponseDto createComment(Long postId, Long userId, String comment) {

        User user = userRepository.findUserByIdOrElseThrow(userId);
        Post post = postRepository.findPostByIdOrElseThrow(postId);

        Comment setcomment = new Comment(comment);
        setcomment.setUser(user);
        setcomment.setPost(post);

        commentRepository.save(setcomment);

        return new CommentResponseDto(
                setcomment.getId(),
                setcomment.getUser().getUserName(),
                setcomment.getComment(),
                setcomment.getCountLike(),
                setcomment.getCreateTime(),
                setcomment.getUpdateTime());
    }

    //댓글 조회
    public Page<CommentResponseDto> findAllComment(Long postId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<Comment> commentPage = commentRepository.findCommentsByPostId(postId, pageable);

        return commentPage.map(comment -> new CommentResponseDto(
                comment.getId(),
                comment.getUser().getUserName(),
                comment.getComment(),
                comment.getCountLike(),
                comment.getCreateTime(),
                comment.getUpdateTime()));
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long userId, Long commentId, String comment) {

        Comment setcomment = commentRepository.findCommentByCommentIdOrElseThrow(commentId);

        if(!setcomment.getUser().getId().equals(userId) && !setcomment.getPost().getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "피드의 작성자나 댓글의 작성자만 수정할 수 있습니다.");
        }
        setcomment.updateComment(comment);

        return new CommentResponseDto(
                setcomment.getId(),
                setcomment.getUser().getUserName(),
                setcomment.getComment(),
                setcomment.getCountLike(),
                setcomment.getCreateTime(),
                setcomment.getUpdateTime());
    }

    //댓글 삭제
    public void deleteComment(Long userId, Long commentId) {

        Comment setcomment = commentRepository.findCommentByCommentIdOrElseThrow(commentId);

        if(!setcomment.getUser().getId().equals(userId) && !setcomment.getPost().getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "피드의 작성자나 댓글의 작성자만 삭제할 수 있습니다.");
        }

        commentRepository.delete(setcomment);
    }

    //댓글 좋아요
    @Transactional
    public void likeComment(Long userId, Long commentId) {
        boolean already = likeRepository.existsByUserIdAndCommentId(userId,commentId);
        Comment comment = commentRepository.findCommentByCommentIdOrElseThrow(commentId);

        //댓글의 작성자는 좋아요를 누를 수 없게 생성
        if(comment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글 작성자는 좋아요를 누를 수 없습니다.");
        }

        if(already) {       //already가 true라면 이미 좋아요를 누른 사용자라고 판단, 좋아요 기록을 삭제하고 좋아요 카운트를 -1
            likeRepository.deleteByUserIdAndCommentId(userId, commentId);
            comment.minusLike();
        } else {            //already가 false라면 좋아요를 누르지 않은 사용자이기 때문에 좋아요 기록을 추가하고 카운트 +1
            Like like = new Like().commentLike(userId, commentId);
            likeRepository.save(like);
            comment.plusLike();
        }
    }
}
