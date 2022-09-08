package com.leonhard.blog.dtos;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

@Data
public class PostDto {
    private long id;

    @NotEmpty(message = "title can not be null")
    @Size(min = 2,message = "Post title should have at least 2 characters")
    private String title;

    @NotEmpty
    @Size(min = 5,message = "Post description should have at least 5 characters")
    private String description;

    @NotEmpty
    private String content;

    private Set<CommentDto> comments;
}
