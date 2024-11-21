package com.example.newsfeedproject.controller.comment;

import com.example.newsfeedproject.dto.comment.CommentRequestDto;
import com.example.newsfeedproject.dto.comment.CommentResponseDto;
import com.example.newsfeedproject.service.comment.CommentService;
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

    @PostMapping("/comments")
    public ResponseEntity<CommentResponseDto> createComment(
            @PathVariable("post_id") Long postId,
            @RequestBody CommentRequestDto dto){

        CommentResponseDto commentResponseDto = commentService.createComment(postId, dto.getUserName(), dto.getComment());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("/comments")
    public ResponseEntity<Page<CommentResponseDto>> findAllComment(
            @PathVariable("post_id") Long postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ){

        Page<CommentResponseDto> commentResponseDtoPage = commentService.findAllComment(postId, page, size);

        return new ResponseEntity<>(commentResponseDtoPage, HttpStatus.OK);
    }

    @PatchMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> updateComment(
            @PathVariable Long id,
            @RequestBody CommentRequestDto dto){

        CommentResponseDto commentResponseDto = commentService.updateComment(id, dto.getComment());

        return new ResponseEntity<>(commentResponseDto, HttpStatus.OK);
    }

    @DeleteMapping("/comments/{id}")
    public ResponseEntity<CommentResponseDto> deleteComment(@PathVariable Long id){
        commentService.deleteComment(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}