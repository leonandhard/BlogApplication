package com.leonhard.blog.services.impl;

import com.leonhard.blog.dtos.CommentDto;
import com.leonhard.blog.exception.BlogAPIException;
import com.leonhard.blog.exception.ResourceNotFoundException;
import com.leonhard.blog.model.Comment;
import com.leonhard.blog.model.Post;
import com.leonhard.blog.repository.CommentRepository;
import com.leonhard.blog.repository.PostRepository;
import com.leonhard.blog.services.CommentService;
import com.leonhard.blog.utils.mapper.CommentMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    private  final CommentMapper commentMapper;

    @Override
    public CommentDto createComment(Long postId, CommentDto commentDto) {

        Comment comment = commentMapper.commentDtoToComment(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "id", postId));

        comment.setPost(post);

        Comment newComment = commentRepository.save(comment);

        return commentMapper.commentToCommentDto(newComment);
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {

        List<Comment> commentList = commentRepository.findByPostId(postId);
        return commentList.stream().map(commentMapper::commentToCommentDto).toList();
    }

    @Override
    public CommentDto getCommentById(Long postId, Long commentId) {

        mapCommentToPost(commentId, postId);

        return commentMapper.commentToCommentDto(mapCommentToPost(commentId, postId));
    }

    @Override
    public CommentDto updateComment(Long postId, Long commentId, CommentDto commentDtoRequest) {

        Comment comment = mapCommentToPost(commentId, postId);

        comment.setBody(commentDtoRequest.getBody());
        comment.setEmail(commentDtoRequest.getEmail());
        comment.setName(commentDtoRequest.getName());

        Comment updateComment = commentRepository.save(comment);
        return commentMapper.commentToCommentDto(updateComment);
    }

    @Override
    public void deleteComment(Long postId, Long commentId) {

        Comment comment = mapCommentToPost(commentId, postId);

        commentRepository.delete(comment);
    }

    private Comment mapCommentToPost(Long commentId, Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFoundException("post", "postId", postId));

        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFoundException("Comment", "commentId", commentId));

        if (!comment.getPost().getId().equals(post.getId())) {
            throw new BlogAPIException(HttpStatus.NOT_FOUND, "comment can not be find in this post");
        }
        return comment;
    }

//    private CommentDto mapToDTO(Comment comment) {
//        CommentDto commentDto = new CommentDto();
//        commentDto.setId(comment.getId());
//        commentDto.setBody(comment.getBody());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
//        return commentDto;
//    }
//
//    private Comment mapToEntity(CommentDto commentDto) {
//        Comment comment = new Comment();
//        comment.setId(commentDto.getId());
//        comment.setBody(commentDto.getBody());
//        comment.setEmail(commentDto.getEmail());
//        comment.setName(commentDto.getName());
//        return comment;
//    }
}
