package com.example.api.services;

import com.example.api.dtos.userDto;
import com.example.api.repository.UserRepository;
import com.example.api.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public CompletableFuture<String> userSignupToken(userDto dto) {
        UUID userUuid = userRepository.getUuidByEmail(dto.getEmail());

        String token = jwtUtils.generateToken(userUuid);

        return CompletableFuture.completedFuture(token);
    }
}
