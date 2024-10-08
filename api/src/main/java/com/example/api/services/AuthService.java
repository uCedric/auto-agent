package com.example.api.services;

import com.example.api.dtos.Dto;
import com.example.api.repository.UserRepository;
import com.example.api.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.UUID;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public CompletableFuture<String> userSignupToken(Dto dto) {
        Map<String, Object> dtoAttributes = dto.getAttributes();

        UUID userUuid = userRepository.getUuidByEmail(dtoAttributes.get("email").toString());

        String token = jwtUtils.generateToken(userUuid);

        return CompletableFuture.completedFuture(token);
    }
}
