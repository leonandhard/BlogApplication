package com.leonhard.blog.jwt;

import com.leonhard.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.crypto.MacProvider;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Service
public class JwtTokenProvider {
    private static final SecretKey jwtSecret = MacProvider.generateKey(SignatureAlgorithm.HS256);
    @Value("${jwt.expire-length}")
    private int jwtExpirationInMs;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date current = new Date();
        Date expireDate = new Date(current.getTime() + jwtExpirationInMs);

        System.out.println("jwtSecret = " + jwtSecret);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(jwtSecret)
                .compact();
    }



}
