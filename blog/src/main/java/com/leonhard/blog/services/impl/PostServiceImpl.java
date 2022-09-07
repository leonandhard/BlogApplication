package com.leonhard.blog.services.impl;

import com.leonhard.blog.dtos.PostDto;
import com.leonhard.blog.dtos.PostPaginationDto;
import com.leonhard.blog.exception.ResourceNotFoundException;
import com.leonhard.blog.model.Post;
import com.leonhard.blog.repository.PostRepository;
import com.leonhard.blog.services.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = mapToEntity(postDto);

        Post newPost = postRepository.save(post);

        return mapToDTO(newPost);
    }

    @Override
    public PostPaginationDto getAllPosts(int pageNO, int pageSize) {

        PageRequest pageable = PageRequest.of(pageNO, pageSize);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();

        List<PostDto> contents = postList.stream().map(this::mapToDTO).toList();

        return PostPaginationDto
                .builder()
                .content(contents)
                .pageNo(posts.getNumber())
                .pageSize(posts.getSize())
                .totalElement(posts.getTotalElements())
                .totalPages(posts.getTotalPages())
                .last(posts.isLast())
                .build();
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        return mapToDTO(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        return mapToDTO(updatePost);

    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));
        postRepository.delete(post);
    }

    private PostDto mapToDTO(Post newPost) {
        PostDto postDto = new PostDto();
        postDto.setContent(newPost.getContent());
        postDto.setDescription(newPost.getDescription());
        postDto.setTitle(newPost.getTitle());
        postDto.setId(newPost.getId());

        return postDto;
    }

    private Post mapToEntity(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
