package com.example.newsfeedproject.service.comment;

import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.entity.comment.Comment;
import com.example.newsfeedproject.entity.post.Post;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.comment.CommentRepository;
import com.example.newsfeedproject.repository.post.PostRepository;
import com.example.newsfeedproject.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

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
                comment.getCreateTime(),
                comment.getUpdateTime()));
    }

    //댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long userId, Long commentId, String comment) {

        Comment setcomment = commentRepository.findCommentByCommentIdOrElseThrow(commentId);

        if(!setcomment.getUser().getId().equals(userId) && !setcomment.getPost().getUser().getId().equals(userId)) {
            throw new RuntimeException("피드의 작성자나 댓글의 작성자만 수정할 수 있습니다.");
        }
        setcomment.updateComment(comment);

        return new CommentResponseDto(
                setcomment.getId(),
                setcomment.getUser().getUserName(),
                setcomment.getComment(),
                setcomment.getCreateTime(),
                setcomment.getUpdateTime());
    }

    //댓글 삭제
    public void deleteComment(Long userId, Long commentId) {

        Comment setcomment = commentRepository.findCommentByCommentIdOrElseThrow(userId);

        if(!setcomment.getUser().getId().equals(userId) && !setcomment.getPost().getUser().getId().equals(userId)) {
            throw new RuntimeException("피드의 작성자나 댓글의 작성자만 수정할 수 있습니다.");
        }

        commentRepository.delete(setcomment);
    }
}
