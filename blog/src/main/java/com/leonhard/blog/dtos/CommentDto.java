package com.leonhard.blog.dtos;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data

public class CommentDto {
    private long id;

    @NotEmpty(message = "Name should not be null")
    private String name;

    @NotEmpty(message = "Email should not be null")
    @Email
    private String email;

    @NotEmpty(message = "Comment body should not be null")
    @Size(min = 10, message = "Comment body should greater than 10 characters")
    private String body;
}
