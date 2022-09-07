package com.leonhard.blog.services.impl;

import com.leonhard.blog.dtos.CommentDto;
import com.leonhard.blog.exception.ResourceNotFoundException;
import com.leonhard.blog.model.Comment;
import com.leonhard.blog.model.Post;
import com.leonhard.blog.repository.CommentRepository;
import com.leonhard.blog.repository.PostRepository;
import com.leonhard.blog.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment = mapToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return mapToDTO(newComment);
    }

    private CommentDto mapToDTO(Comment comment) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setBody(comment.getBody());
        commentDto.setName(comment.getName());
        commentDto.setEmail(comment.getEmail());
        return commentDto;
    }

    private Comment mapToEntity(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setId(commentDto.getId());
        comment.setBody(commentDto.getBody());
        comment.setEmail(commentDto.getEmail());
        comment.setName(commentDto.getName());
        return comment;
    }
}
