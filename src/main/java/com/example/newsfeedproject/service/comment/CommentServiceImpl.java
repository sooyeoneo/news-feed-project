package com.example.newsfeedproject.service.comment;

import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.dto.comment.LikeCommentResponseDto;
import com.example.newsfeedproject.entity.comment.Comment;
import com.example.newsfeedproject.entity.like.Like;
import com.example.newsfeedproject.entity.post.Post;
import com.example.newsfeedproject.entity.user.User;
import com.example.newsfeedproject.repository.like.LikeRepository;
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

    // 댓글 생성, 특정 게시글(postId)에 대해 사용자가 댓글을 생성
    public CommentResponseDto createComment(Long postId, Long userId, String comment) {

        User user = userRepository.findUserByIdOrElseThrow(userId); // 사용자 존재 확인
        Post post = postRepository.findPostByIdOrElseThrow(postId); // 게시글 존재 확인

        // 댓글 객체 생성 및 사용자, 게시글 설정
        Comment setcomment = new Comment(comment);
        setcomment.setUser(user);
        setcomment.setPost(post);

        commentRepository.save(setcomment); // DB에 저장

        // 생성된 댓글 정보를 반환
        return new CommentResponseDto(
                setcomment.getId(),
                setcomment.getUser().getUserName(),
                setcomment.getComment(),
                setcomment.getCountLike(),
                setcomment.getCreateTime(),
                setcomment.getUpdateTime());
    }

    // 댓글 조회, 특정 게시글의 모든 댓글ㅇ르 페이징 처리하여 반환
    public Page<CommentResponseDto> findAllComment(Long postId, int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Comment> commentPage = commentRepository.findCommentsByPostId(postId, pageable);

        // Comment 엔티티를 DTO로 변환하여 반환
        return commentPage.map(comment -> new CommentResponseDto(
                comment.getId(),
                comment.getUser().getUserName(),
                comment.getComment(),
                comment.getCountLike(),
                comment.getCreateTime(),
                comment.getUpdateTime()));
    }

    // 댓글 수정, 사용자는 자신이 작성한 댓글 또는 게시글의 작성자일 때만 수정 가능
    @Transactional
    public CommentResponseDto updateComment(Long userId, Long commentId, String comment) {

        Comment setcomment = commentRepository.findCommentByCommentIdOrElseThrow(commentId);

        // 작성자 검증 (댓글 작성자 또는 게시글 작성자만 가능)
        if(!setcomment.getUser().getId().equals(userId) && !setcomment.getPost().getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "피드의 작성자나 댓글의 작성자만 수정할 수 있습니다.");
        }
        // 댓글 내용 수정
        setcomment.updateComment(comment);

        // 수정된 댓글 정보 반환
        return new CommentResponseDto(
                setcomment.getId(),
                setcomment.getUser().getUserName(),
                setcomment.getComment(),
                setcomment.getCountLike(),
                setcomment.getCreateTime(),
                setcomment.getUpdateTime());
    }

    // 댓글 삭제, 사용자는 자신이 작성한 댓글 또는 게시글 작성자만 가능)
    public void deleteComment(Long userId, Long commentId) {

        Comment setcomment = commentRepository.findCommentByCommentIdOrElseThrow(commentId);

        // 작성자 검증 (댓글 작성자 또는 게시글 작성자만 가능)
        if(!setcomment.getUser().getId().equals(userId) && !setcomment.getPost().getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "피드의 작성자나 댓글의 작성자만 삭제할 수 있습니다.");
        }

        // 댓글 삭제
        commentRepository.delete(setcomment);
    }

    // 댓글 좋아요 추가/취소, 사용자는 자신의 댓글에 좋아요 누를 수 없음
    @Transactional
    public LikeCommentResponseDto likeComment(Long userId, Long commentId) {
        boolean already = likeRepository.existsByUserIdAndCommentId(userId,commentId);
        Comment comment = commentRepository.findCommentByCommentIdOrElseThrow(commentId);

        // 댓글의 작성자는 좋아요를 누를 수 없게 생성
        if(comment.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "댓글 작성자는 좋아요를 누를 수 없습니다.");
        }

        // 좋아요 처리 : 이미 눌렀다면 취소, 아니면 추가
        if(already) {       // already가 true라면 이미 좋아요를 누른 사용자라고 판단, 좋아요 기록을 삭제하고 좋아요 카운트를 -1
            likeRepository.deleteByUserIdAndCommentId(userId, commentId);
            comment.minusLike();
        } else {            // already가 false라면 좋아요를 누르지 않은 사용자이기 때문에 좋아요 기록을 추가하고 카운트 +1
            comment.plusLike();
            Like like = new Like().commentLike(userId, commentId);
            likeRepository.save(like); // 좋아요 기록 저장
        }

        // 업데이트된 좋아요 개수 반환
        return new LikeCommentResponseDto(comment.getCountLike());
    }
}
