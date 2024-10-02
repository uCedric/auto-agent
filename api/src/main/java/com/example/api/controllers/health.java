package com.example.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class health {

    @GetMapping("/health")
    public String checkHealth() {
        return "spring-boot is running";
    }
}
