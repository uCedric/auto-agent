package com.example.api.controllers;

import com.example.api.dtos.promptDto;
import com.example.api.services.PromptService;
import com.example.api.validators.AuthValidator;
import com.example.api.validators.BodyValidator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.concurrent.CompletableFuture;
import jakarta.servlet.http.HttpServletResponse;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/prompts")
public class PromptController {

    @Autowired
    private PromptService promptService;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private BodyValidator bodyValidator;

    @PostMapping("/query")
    public CompletableFuture<Flux<String>> query(@RequestHeader("Authorization") String token,
            @RequestBody promptDto prompt, HttpServletResponse response) throws Exception {
        authValidator.validateToken(token, response);

        bodyValidator.validate(prompt);

        CompletableFuture<Flux<String>> result = promptService.query(prompt, response.getHeader("userUuid"));

        return result;
    }
}
