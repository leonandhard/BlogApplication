package com.leonhard.blog.controller;

import com.leonhard.blog.dtos.CommentDto;
import com.leonhard.blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/posts/{postId}/comment")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long postId,
                                                    @RequestBody CommentDto commentDto) {

        return new ResponseEntity<>(commentService.createComment(postId, commentDto), HttpStatus.CREATED);
    }
}
