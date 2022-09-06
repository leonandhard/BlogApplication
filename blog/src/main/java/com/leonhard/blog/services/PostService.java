package com.leonhard.blog.services;

import com.leonhard.blog.dtos.PostDto;

public interface PostService {
    PostDto createPost(PostDto postDto);
}
