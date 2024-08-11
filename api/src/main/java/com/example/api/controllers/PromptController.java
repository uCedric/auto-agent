package com.example.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.api.pojos.Prompt;
import com.example.api.services.PromptService;
import com.example.api.validators.BodyValidator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/prompt")
public class PromptController {

    @PostMapping("/query")
    public String query(@RequestBody Prompt prompt) {
        // 1. auth check
        // 2. data validation
        BodyValidator bodyValidator = new BodyValidator();
        bodyValidator.validate(prompt);
        // 3. business logic
        PromptService promptService = new PromptService();
        String response = promptService.query(prompt.getPrompt());
        // 4. response
        return response;
    }

    @PostMapping("/history")
    public String getHistory(@RequestBody String entity) {

        return entity;
    }

}
