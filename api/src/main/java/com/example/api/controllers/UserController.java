package com.example.api.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.api.validators.AuthValidator;

@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping("/all")
    public String getUser(@RequestHeader("Authorization") String token) {
        AuthValidator authValidator = new AuthValidator();
        authValidator.validateToken(token);

        return "Hello User!";
    }

}
