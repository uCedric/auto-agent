package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import com.example.api.dtos.userDto;
import com.example.api.utils.JwtUtils;
import com.example.api.utils.RedisUtils;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private RedisUtils redisUtils;

    public CompletableFuture<String> userSignupToken(userDto dto) {
        String token = jwtUtils.generateToken(dto);

        redisUtils.add(dto.getEmail(), token);

        return CompletableFuture.completedFuture(token);
    }
}
