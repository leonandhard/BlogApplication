package com.leonhard.blog.services;

import com.leonhard.blog.dtos.PostDto;
import com.leonhard.blog.dtos.PostPaginationDto;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostPaginationDto getAllPosts(int pageNo, int pageSize,String sortBy,String sortDir);

    PostDto getPostById(Long id);

    PostDto updatePost(PostDto postDto, Long id);

    void deletePost(Long id);
}
