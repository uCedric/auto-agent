package com.example.api.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.api.dtos.signupDto;
import com.example.api.services.AuthService;
import com.example.api.services.userService;
import com.example.api.utils.AsyncProcessor;
import com.example.api.utils.SuccessResponse;
import com.example.api.validators.AuthValidator;
import com.example.api.validators.BodyValidator;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private BodyValidator bodyValidator;

    @Autowired
    private userService userService;

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public SuccessResponse<String> signup(@RequestBody signupDto signupDto)
            throws InterruptedException, ExecutionException {
        // validate body
        bodyValidator.validate(signupDto);
        AsyncProcessor.<signupDto, Boolean>init()
                .addTask(userService::signup, signupDto)
                .process().get();

        String token = authService.userSignupToken(signupDto);
        // TODO: add token to redis

        SuccessResponse<String> response = new SuccessResponse<>(200, "User signed up successfully", token);

        return response;

    }

    @GetMapping("/all")
    public String getUser(@RequestHeader("Authorization") String token) {
        // TODO: develop auth exception class
        AuthValidator authValidator = new AuthValidator();
        authValidator.validateToken(token);

        return "Hello User!";
    }

}
