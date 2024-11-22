package com.example.newsfeedproject.dto.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class CommentRequestDto {

    private final String comment;

    public CommentRequestDto(@JsonProperty("comment") String comment) {
        this.comment = comment;
    }
}
