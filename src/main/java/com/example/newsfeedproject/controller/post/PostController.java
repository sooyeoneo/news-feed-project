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

    @PostMapping
    public ResponseEntity<PostResponseDto> createPost(@RequestBody CreatePostRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        PostResponseDto postResponseDto =
                postService.createPost(
                        loginUser.getUserName(),
                        dto.getTitle(),
                        dto.getContents()
                );

        return new ResponseEntity<>(postResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Page<PostResponseDto>> findAllPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){

        Page<PostResponseDto> postResponseDtoPage = postService.findAllPost(page, size);

        return new ResponseEntity<>(postResponseDtoPage, HttpStatus.OK);
    }

    @GetMapping("/friends")
    public ResponseEntity<Page<PostResponseDto>> friendPost(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestBody FriendPostsReqestDto friendPostsReqestDto,
            HttpServletRequest request
    ){

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        Page<PostResponseDto> friendPostsDto = postService.findFriendPost(
                page,size,loginUser.getUserId(),friendPostsReqestDto.getFriendId());

        return new ResponseEntity<>(friendPostsDto, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostResponseDto> updatePost(@PathVariable Long id, @RequestBody UpdatePostRequestDto dto, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        PostResponseDto postResponseDto = postService.updatePost(loginUser.getUserId(), id, dto.getTitle(), dto.getContents());

        return new ResponseEntity<>(postResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<PostResponseDto> deletePost(@PathVariable Long id, HttpServletRequest request) {

        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        postService.deletePost(loginUser.getUserId(), id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 좋아요 기능
    @PostMapping("/{id}")
    public ResponseEntity<LikePostResponseDto> likePost(@PathVariable Long id, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");

        LikePostResponseDto likePostResponseDto = postService.likePost(loginUser.getUserId(), id);

        return new ResponseEntity<>(likePostResponseDto, HttpStatus.OK);

    }
}
