package com.leonhard.blog.services.impl;

import com.leonhard.blog.dtos.PostDto;
import com.leonhard.blog.model.Post;
import com.leonhard.blog.repository.PostRepository;
import com.leonhard.blog.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    private PostRepository postRepository;

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
    public List<PostDto> getAllPosts() {

        List<Post> posts = postRepository.findAll();

        return posts.stream().map(this::mapToDTO).collect(Collectors.toList());
    }
    private PostDto mapToDTO(Post newPost) {
        PostDto postDto = new PostDto();
        postDto.setContent(newPost.getContent());
        postDto.setDescription(newPost.getDescription());
        postDto.setTitle(newPost.getTitle());
        postDto.setId(newPost.getId());

        return postDto;
    }
    private Post mapToEntity (PostDto postDto){
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());
        return post;
    }

}
