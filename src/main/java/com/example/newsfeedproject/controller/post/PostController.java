package com.example.newsfeedproject.controller.post;

import com.example.newsfeedproject.dto.login.LoginResponseDto;
import com.example.newsfeedproject.dto.post.*;
import com.example.newsfeedproject.service.post.PostService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    // 게시물 생성
    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false); // 세션 가져옴
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER"); // 로그인된 사용자 정보 조회

        // 게시물 생성 서비스 호출
        PostResponseDto postResponseDto =
                postService.createPost(
                        loginUser.getUserName(),
                        dto.getTitle(),
                        dto.getContents()
                );

        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED); // 생성된 게시물 반환
    }

    // 게시물 전체 조회
    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findAllPost(
            @RequestParam(defaultValue = "0") int page, // 페이지 번호 기본값 설정
            @RequestParam(defaultValue = "10") int size // 페이지 크기 기본값 설정
    ){
        // 게시물 조회 서비스 호출
        Page<PostResponseDto> postResponseDtoPage = postService.findAllPost(page, size);

        return new ResponseEntity<>(postResponseDtoPage, HttpStatus.OK); // 조회된 게시물 목록 반환
    }

    // 친구의 게시물 조회
    @GetMapping("/friends")
    public ResponseEntity<Page<PostResponseDto>> friendPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody FriendPostsReqestDto friendPostsReqestDto, // 친구 정보 요청 데이터
            HttpServletRequest request
    ){

        HttpSession session = request.getSession(false); // 세션 가져옴
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 친구 게시물 조회 서비스 호출
        Page<PostResponseDto> friendPostsDto = postService.findFriendPost(
                page,size,loginUser.getUserId(),friendPostsReqestDto.getFriendId());

        return new ResponseEntity<>(friendPostsDto, HttpStatus.OK); // 친구 게시물 반환
    }

    // 게시물 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 게시물 수정 서비스 호출
        PostResponseDto postResponseDto = postService.updatePost(loginUser.getUserId(), id, dto.getTitle(), dto.getContents());

        return new ResponseEntity<>(postResponseDto, HttpStatus.OK); // 수정된 게시물 반환
    }

    // 게시물 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        postService.deletePost(loginUser.getUserId(), id); // 게시물 삭제 서비스 호출

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 게시물 좋아요 기능
    @PostMapping("/{id}")
    public ResponseEntity<LikePostResponseDto> likePost(@PathVariable Long id, HttpServletRequest request){
        HttpSession session = request.getSession(false); // 세션 가져옴
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        // 좋아요 서비스 호출
        LikePostResponseDto likePostResponseDto = postService.likePost(loginUser.getUserId(), id);

        return new ResponseEntity<>(likePostResponseDto, HttpStatus.OK); // 좋아요 결과 반환

    }
}
