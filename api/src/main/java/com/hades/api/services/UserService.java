package com.hades.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hades.api.dtos.Dto;
import com.hades.api.repository.UserRepository;
import com.hades.api.utils.Exceptions.InternalServerException;
import com.hades.api.utils.Exceptions.InvalidParameterException;

import java.util.concurrent.CompletableFuture;
import java.util.Map;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Async("TaskThread")
    public CompletableFuture<String> signup(Dto signupDto)
            throws InvalidParameterException, InternalServerException {
        System.out.println("get attributes" + signupDto);
        Map<String, Object> signupAttributes = signupDto.getAttributes();

        isEmailDuplicated(signupAttributes.get("email").toString());
        addUser(signupAttributes);

        return CompletableFuture.completedFuture(null);
    }

    public Boolean isEmailDuplicated(String email) {
        int duplicatedUserCount = userRepository.searchUserCountByEmail(email);

        if (duplicatedUserCount > 0) {
            throw new InvalidParameterException("user existed.");
        }

        return true;
    }

    public Boolean addUser(Map<String, Object> signupAttributes) {
        UUID id = UUID.randomUUID();

        String hashedPassword = new BCryptPasswordEncoder().encode(signupAttributes.get("password").toString());
        int effectedRow = userRepository.addUser(id, signupAttributes.get("name").toString(),
                signupAttributes.get("email").toString(),
                hashedPassword);

        if (effectedRow != 1)
            throw new InternalServerException("Failed to access");

        return true;
    }

    @Async("TaskThread")
    public CompletableFuture<String> login(Dto loginDto)
            throws InvalidParameterException, InternalServerException {
        Thread currentThread = Thread.currentThread();
        System.out.println("service thread name: " + currentThread.getName());
        Map<String, Object> loginAttributes = loginDto.getAttributes();

        String dbPassword = userRepository.getUserPasswordByEmail(loginAttributes.get("email").toString());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginAttributes.get("password").toString(), dbPassword)) {
            throw new InvalidParameterException("invalid password.");
        }

        return CompletableFuture.completedFuture(null);
    }
}
