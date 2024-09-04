package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import com.example.api.dtos.signupDto;
import com.example.api.utils.JwtUtils;
import com.example.api.utils.RedisUtils;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    public CompletableFuture<String> userSignupToken(signupDto signupDto) {
        String token = jwtUtils.generateToken(signupDto);

        redisUtils.add(signupDto.getEmail(), token);

        return CompletableFuture.completedFuture(token);
    }

    public String isValidToken(String token) {
        return jwtUtils.isValidToken(token);
    }
}
