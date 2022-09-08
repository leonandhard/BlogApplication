package com.leonhard.blog.services.impl;

import com.leonhard.blog.dtos.PostDto;
import com.leonhard.blog.dtos.PostPaginationDto;
import com.leonhard.blog.exception.ResourceNotFoundException;
import com.leonhard.blog.model.Post;
import com.leonhard.blog.repository.PostRepository;
import com.leonhard.blog.services.PostService;
import com.leonhard.blog.utils.mapper.PostMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    private final PostMapper postMapper;

    @Override
    public PostDto createPost(PostDto postDto) {

        Post post = postMapper.toPostEntity(postDto);
        System.out.println("post = " + post);
        Post newPost = postRepository.save(post);

        return postMapper.toPostDto(newPost);
    }

    @Override
    public PostPaginationDto getAllPosts(int pageNO, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        PageRequest pageable = PageRequest.of(pageNO, pageSize, sort);

        Page<Post> posts = postRepository.findAll(pageable);

        List<Post> postList = posts.getContent();

        List<PostDto> contents = postList.stream().map(postMapper::toPostDto).toList();

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
        return postMapper.toPostDto(post);
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post updatePost = postRepository.save(post);
        return postMapper.toPostDto(updatePost);

    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("post", "id", id));

        postRepository.delete(post);
    }

//    private PostDto mapToDTO(Post newPost) {
//        PostDto postDto = new PostDto();
//        postDto.setContent(newPost.getContent());
//        postDto.setDescription(newPost.getDescription());
//        postDto.setTitle(newPost.getTitle());
//        postDto.setId(newPost.getId());
//
//        return postDto;
//    }
//
//    private Post mapToEntity(PostDto postDto) {
//        Post post = new Post();
//        post.setTitle(postDto.getTitle());
//        post.setDescription(postDto.getDescription());
//        post.setContent(postDto.getContent());
//        return post;
//    }

}
