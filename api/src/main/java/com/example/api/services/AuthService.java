package com.example.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

import com.example.api.dtos.userDto;
import com.example.api.utils.JwtUtils;

@Service
public class AuthService {

    @Autowired
    private JwtUtils jwtUtils;

    public CompletableFuture<String> userSignupToken(userDto dto) {
        String token = jwtUtils.generateToken(dto);

        return CompletableFuture.completedFuture(token);
    }
}
