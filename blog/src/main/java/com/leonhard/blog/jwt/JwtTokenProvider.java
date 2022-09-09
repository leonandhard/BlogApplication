package com.leonhard.blog.jwt;

import com.leonhard.blog.exception.BlogAPIException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Autowired
    private SecretKey jwtSecret;
    @Value("${jwt.expire-length}")
    private int jwtExpirationInMs;


    public String generateToken(Authentication authentication) {
        String username = authentication.getName();
        Date current = new Date();
        Date expireDate = new Date(current.getTime() + jwtExpirationInMs);

        System.out.println("current = " + authentication);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expireDate)
                .signWith(jwtSecret)
                .compact();
    }
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecret).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT signature");
        } catch (MalformedJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Invalid JWT token");
        } catch (ExpiredJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Expired JWT token");
        } catch (UnsupportedJwtException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "Unsupported JWT token");
        } catch (IllegalArgumentException e) {
            throw new BlogAPIException(HttpStatus.BAD_REQUEST, "JWT claims string is empty");
        }

    }

    public String getUsernameFromJWT(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }


}
