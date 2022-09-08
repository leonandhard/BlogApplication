package com.leonhard.blog.controller;

import com.leonhard.blog.dtos.CommentDto;
import com.leonhard.blog.services.CommentService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/posts/{postId}/comment")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable Long postId) {

        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }

    @GetMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable(value = "commentId") Long commentId,
                                                     @PathVariable(value = "postId") Long postId) {

        CommentDto commentDto = commentService.getCommentById(postId,commentId);
        return ResponseEntity.ok(commentDto);
    }

    @PutMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable(value = "postId") Long postId,
                                                    @PathVariable(value = "commentId") Long commentId,
                                                    @RequestBody CommentDto commentDto){

        CommentDto updateComment = commentService.updateComment(postId,commentId,commentDto);

        return ResponseEntity.ok(updateComment);
    }

    @DeleteMapping("/posts/{postId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable Long postId,
                                                @PathVariable Long commentId){

        commentService.deleteComment(postId,commentId);
        return ResponseEntity.ok("Comment deleted successfully");
    }
}
