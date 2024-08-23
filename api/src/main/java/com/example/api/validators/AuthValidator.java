package com.example.api.validators;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

public class AuthValidator {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void validateToken(String token) {
        if (isTokenEmpty(token))
            throw new IllegalArgumentException("Token is empty");
        if (isTokenNotInJwtFormate(token))
            throw new IllegalArgumentException("Token is not in JWT format");
        if (isTokenInvalid(token))
            throw new IllegalArgumentException("Token is invalid");
    }

    private boolean isTokenEmpty(String token) {
        return token.isEmpty();
    }

    private boolean isTokenNotInJwtFormate(String token) {
        String[] splitedToken = token.split(" ");

        return splitedToken.length != 2 || !splitedToken[0].equals("Bearer");
    }

    private boolean isTokenInvalid(String token) {
        return !redisTemplate.hasKey(token);
    }
}
