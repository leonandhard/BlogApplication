package com.leonhard.blog.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class LoginDto {

    private String usernameOrEmail;

    private String password;
}
