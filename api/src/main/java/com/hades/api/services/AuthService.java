package com.hades.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hades.api.dtos.Dto;
import com.hades.api.repository.UserRepository;
import com.hades.api.utils.JwtUtils;

import java.util.concurrent.CompletableFuture;
import java.util.UUID;
import java.util.Map;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

    @Async("TaskThread")
    public CompletableFuture<String> userSignupToken(Dto dto) {
        Map<String, Object> dtoAttributes = dto.getAttributes();

        UUID userUuid = userRepository.getUuidByEmail(dtoAttributes.get("email").toString());

        String token = jwtUtils.generateToken(userUuid);

        return CompletableFuture.completedFuture(token);
    }
}
