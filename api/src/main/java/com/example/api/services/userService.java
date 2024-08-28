package com.example.api.services;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.UUID;

import com.example.api.dtos.signupDto;
import com.example.api.repository.UserRepository;
import com.example.api.utils.Exceptions.InternalServerException;
import com.example.api.utils.Exceptions.InvalidParameterException;

@Service
public class userService {

    @Autowired
    private UserRepository userRepository;

    @Async("dbAsyncExecutor")
    public CompletableFuture<Boolean> signup(signupDto signupDto)
            throws InvalidParameterException, InternalServerException {
        isEmailDuplicated(signupDto.getEmail());
        addUser(signupDto);

        return CompletableFuture.completedFuture(true);
    }

    public Boolean isEmailDuplicated(String email) {
        int duplicatedUserCount = userRepository.searchUserByEmail(email);

        if (duplicatedUserCount > 0) {
            // TODO: add exception
            System.out.println("////////////////////////////////////////////////" + duplicatedUserCount);
            throw new InvalidParameterException("user existed.");
        }

        return true;
    }

    public Boolean addUser(signupDto signupDto) {
        UUID id = UUID.randomUUID();

        int effectedRow = userRepository.addUser(id, signupDto.getName(), signupDto.getEmail(),
                signupDto.getPassword());
        if (effectedRow != 1)
            throw new InternalServerException("Failed to access");

        return true;
    }
}
