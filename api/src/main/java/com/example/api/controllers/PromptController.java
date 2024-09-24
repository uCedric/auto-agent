package com.example.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.api.dtos.dataDto;
import com.example.api.dtos.llmResDto;
import com.example.api.dtos.promptDto;
import com.example.api.services.PromptService;
import com.example.api.utils.SuccessResponse;
import com.example.api.validators.AuthValidator;
import com.example.api.validators.BodyValidator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

import io.jsonwebtoken.Claims;

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
    public SuccessResponse<dataDto> query(@RequestHeader("authorization") String token, @RequestBody promptDto prompt)
            throws Exception {
        Claims tokenContent = authValidator.validateToken(token);
        // tokenContent.get("role", String.class);
        bodyValidator.validate(prompt);

        CompletableFuture<llmResDto> response = promptService.query(RequestMethod.POST, prompt);
        llmResDto result = response.get();

        SuccessResponse<dataDto> successResponse = new SuccessResponse<>(200, "Success", result.getData());

        return successResponse;
    }

    @PostMapping("/history")
    public String getHistory(@RequestBody String entity) {

        return entity;
    }

}
