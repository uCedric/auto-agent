package com.example.api.services;

import com.example.api.dtos.userDto;
import com.example.api.repository.UserRepository;
import com.example.api.utils.Exceptions.InternalServerException;
import com.example.api.utils.Exceptions.InvalidParameterException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Async("dbAsyncExecutor")
    public CompletableFuture<String> signup(userDto signupDto)
            throws InvalidParameterException, InternalServerException {

        isEmailDuplicated(signupDto.getEmail());
        addUser(signupDto);

        return CompletableFuture.completedFuture(null);
    }

    public Boolean isEmailDuplicated(String email) {
        int duplicatedUserCount = userRepository.searchUserCountByEmail(email);

        if (duplicatedUserCount > 0) {
            throw new InvalidParameterException("user existed.");
        }

        return true;
    }

    public Boolean addUser(userDto signupDto) {
        UUID id = UUID.randomUUID();

        String hashedPassword = new BCryptPasswordEncoder().encode(signupDto.getPassword());
        int effectedRow = userRepository.addUser(id, signupDto.getName(), signupDto.getEmail(),
                hashedPassword);

        if (effectedRow != 1)
            throw new InternalServerException("Failed to access");

        return true;
    }

    @Async("dbAsyncExecutor")
    public CompletableFuture<String> login(userDto loginDto)
            throws InvalidParameterException, InternalServerException {
        String dbPassword = userRepository.getUserPasswordByEmail(loginDto.getEmail());

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(loginDto.getPassword(), dbPassword)) {
            throw new InvalidParameterException("invalid password.");
        }

        return CompletableFuture.completedFuture(null);
    }
}
