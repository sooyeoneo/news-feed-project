package com.example.newsfeedproject.controller.comment;

import com.example.newsfeedproject.dto.comment.CommentRequestDto;
import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.dto.comment.LikeCommentResponseDto;
import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.service.comment.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts/{post_id}")
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable("post_id") Long postId,
            @RequestBody CommentRequestDto dto,
            // HTTP 요청 정보 사용
            HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션을 가져옴

        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER"); // 로그인 된 사용자 정보 조회
        CommentResponseDto commentResponseDto = commentService.createComment(postId, loginUser.getUserId(), dto.getComment()); // 댓글 생성 서비스 호출
        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);  // 생성된 댓글 응답 반환
    }

    // 댓글 전체 조회
    @GetMapping("/comments")
    public ResponseEntity<Page<CommentResponseDto>> findAllComment(
            @PathVariable("post_id") Long postId, // 요청 URL에서 게시물 ID를 가져옴
            @RequestParam(defaultValue = "0") int page, // 페이지 번호 기본값 설정
            @RequestParam(defaultValue = "10") int size // 페이지 크기 기본값 설정
    ){
        // 댓글 목록 조회 서비스 호출
        Page<CommentResponseDto> commentResponseDtoPage = commentService.findAllComment(postId, page, size);

        return new ResponseEntity<>(commentResponseDtoPage, HttpStatus.OK); // 조회된 댓글 목록 반환
    }

    // 댓글 수정
    @PutMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto dto,
            HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 댓글 수정 서비스 호출
        CommentResponseDto commentResponseDto = commentService.updateComment(loginUser.getUserId(), id, dto.getComment());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK); // 수정된 댓글 반환
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        commentService.deleteComment(loginUser.getUserId(), id); // 댓글 삭제 서비스 호출

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 댓글 좋아요 기능
    @PostMapping("/comments/{id}")
    public ResponseEntity<LikeCommentResponseDto> likeComment(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 좋아요 서비스 호출
        LikeCommentResponseDto likeCommentResponseDto = commentService.likeComment(loginUser.getUserId(), id);

        return new ResponseEntity<>(likeCommentResponseDto, HttpStatus.OK); // 좋아요 결과 반환
    }
}
