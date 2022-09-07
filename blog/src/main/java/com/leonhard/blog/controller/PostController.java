package com.leonhard.blog.controller;

import com.leonhard.blog.dtos.PostDto;
import com.leonhard.blog.dtos.PostPaginationDto;
import com.leonhard.blog.services.PostService;
import com.leonhard.blog.utils.AppConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

//    public PostController(PostService postService) {
//        this.postService = postService;
//    }

    @PostMapping
    public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto) {

        return new ResponseEntity<>(postService.createPost(postDto), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PostPaginationDto> getAllPosts(
            @RequestParam(value = "pageNo", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = AppConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = AppConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = AppConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir
    ) {
        return ResponseEntity.ok(postService.getAllPosts(pageNo, pageSize, sortBy, sortDir));
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(
            @PathVariable(name = "id") Long id) {
        return ResponseEntity.ok(postService.getPostById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(
            @RequestBody PostDto postDto
            , @PathVariable Long id) {

        PostDto postResponse = postService.updatePost(postDto, id);
        return ResponseEntity.ok(postResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(
            @PathVariable Long id) {

        postService.deletePost(id);

        return ResponseEntity.ok("This Post has been deleted...");
    }


}
