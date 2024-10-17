package com.hades.api.controllers;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hades.api.dtos.Dto;
import com.hades.api.dtos.loginDto;
import com.hades.api.dtos.signupDto;
import com.hades.api.dtos.tokenDto;
import com.hades.api.services.AuthService;
import com.hades.api.services.UserService;
import com.hades.api.utils.AsyncService;
import com.hades.api.utils.SuccessResponse;
import com.hades.api.validators.BodyValidator;
import com.hades.api.utils.AsyncExecutor;

@RestController
@RequestMapping("/users")
public class UserController {
        @Autowired
        private BodyValidator bodyValidator;

        @Autowired
        private UserService userService;

        @Autowired
        private AuthService authService;

        @Autowired
        private AsyncExecutor asyncExecutor;

        @PostMapping("/signup")
        public SuccessResponse<Map<String, String>> signup(@RequestBody signupDto signupDto)
                        throws InterruptedException, ExecutionException {
                bodyValidator.validate(signupDto);

                AsyncService<Dto, String> asyncServices = AsyncService.<Dto, String>init()
                                .addTask(userService::signup, signupDto);

                asyncExecutor.process(asyncServices).get();

                SuccessResponse<Map<String, String>> response = new SuccessResponse<>(200,
                                "User signed up successfully");

                return response;

        }

        @PostMapping("/login")
        public SuccessResponse<Map<String, String>> login(@RequestBody loginDto loginDto)
                        throws InterruptedException, ExecutionException {
                Thread currentThread = Thread.currentThread();
                System.out.println("controller thread name: " + currentThread.getName());
                bodyValidator.validate(loginDto);

                AsyncService<Dto, String> asyncServices = AsyncService.<Dto, String>init()
                                .addTask(userService::login, loginDto)
                                .addTask(authService::userSignupToken, loginDto);

                Map<String, String> asyncResults = asyncExecutor.process(asyncServices).get();

                String result = asyncResults.get("task2");

                SuccessResponse<Map<String, String>> response = new SuccessResponse<>(200,
                                "User signed up successfully",
                                new tokenDto(result).getToken());

                return response;
        }
}
