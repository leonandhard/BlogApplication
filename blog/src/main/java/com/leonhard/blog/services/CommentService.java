package com.leonhard.blog.services;

import com.leonhard.blog.dtos.CommentDto;
import com.leonhard.blog.model.Comment;

public interface CommentService {
     CommentDto createComment(Long postId, CommentDto commentDto);
}
