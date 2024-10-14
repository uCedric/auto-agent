package com.hades.api.controllers;

import java.util.concurrent.ExecutionException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hades.api.dtos.Dto;
import com.hades.api.dtos.loginDto;
import com.hades.api.dtos.signupDto;
import com.hades.api.dtos.tokenDto;
import com.hades.api.services.AuthService;
import com.hades.api.services.UserService;
import com.hades.api.utils.AsyncProcessor;
import com.hades.api.utils.SuccessResponse;
import com.hades.api.validators.BodyValidator;

import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/users")
public class UserController {

        @Autowired
        private BodyValidator bodyValidator;

        @Autowired
        private UserService userService;

        @Autowired
        private AuthService authService;

        @PostMapping("/signup")
        public SuccessResponse<Map<String, String>> signup(@RequestBody signupDto signupDto)
                        throws InterruptedException, ExecutionException {
                bodyValidator.validate(signupDto);

                AsyncProcessor.<Dto, String>init()
                                .addTask(userService::signup, signupDto)
                                .process().get();

                SuccessResponse<Map<String, String>> response = new SuccessResponse<>(200,
                                "User signed up successfully");

                return response;

        }

        @PostMapping("/login")
        public SuccessResponse<Map<String, String>> login(@RequestBody loginDto loginDto)
                        throws InterruptedException, ExecutionException {
                bodyValidator.validate(loginDto);

                Map<String, String> asyncResults = AsyncProcessor.<Dto, String>init()
                                .addTask(userService::login, loginDto)
                                .addTask(authService::userSignupToken, loginDto)
                                .process().get();
                String result = asyncResults.get("task2");

                SuccessResponse<Map<String, String>> response = new SuccessResponse<>(200,
                                "User signed up successfully",
                                new tokenDto(result).getToken());

                return response;
        }
}
