package com.leonhard.blog.jwt;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JWTAuthResponse {

    private final String accessToken;
    private final String tokenType = "Bearer";

}
