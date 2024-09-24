package com.example.api.controllers;

import java.util.concurrent.ExecutionException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.api.dtos.signupDto;
import com.example.api.dtos.tokenDto;
import com.example.api.dtos.loginDto;
import com.example.api.dtos.userDto;
import com.example.api.services.AuthService;
import com.example.api.services.UserService;
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
        private UserService userService;

        @Autowired
        private AuthService authService;

        @PostMapping("/signup")
        public SuccessResponse<Map<String, String>> signup(@RequestBody signupDto dto)
                        throws InterruptedException, ExecutionException {
                userDto signupDto = new signupDto(dto.getName(), dto.getEmail(), dto.getPassword());

                bodyValidator.validate(signupDto);

                Map<String, String> results = AsyncProcessor.<userDto, String>init()
                                .addTask(userService::signup, signupDto)
                                .addTask(authService::userSignupToken, signupDto)
                                .process().get();

                String token = results.get("task2");
                SuccessResponse<Map<String, String>> response = new SuccessResponse<>(200,
                                "User signed up successfully",
                                new tokenDto(token).getToken());

                return response;

        }

        @PostMapping("/login")
        public SuccessResponse<Map<String, String>> login(@RequestBody loginDto dto)
                        throws InterruptedException, ExecutionException {
                userDto loginDto = new loginDto(dto.getEmail(), dto.getPassword());

                bodyValidator.validate(loginDto);

                Map<String, String> asyncResults = AsyncProcessor.<userDto, String>init()
                                .addTask(userService::login, loginDto)
                                .addTask(authService::userSignupToken, loginDto)
                                .process().get();
                String result = asyncResults.get("task2");

                SuccessResponse<Map<String, String>> response = new SuccessResponse<>(200,
                                "User signed up successfully",
                                new tokenDto(result).getToken());

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
