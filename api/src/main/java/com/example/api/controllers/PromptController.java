package com.example.api.controllers;

import org.springframework.web.bind.annotation.RestController;

import com.example.api.dtos.llmReqDto;
import com.example.api.dtos.promptDto;
import com.example.api.services.PromptService;
import com.example.api.validators.BodyValidator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMethod;

@RestController
@RequestMapping("/prompt")
public class PromptController {

    @Autowired
    private PromptService promptService;

    @PostMapping("/query")
    public llmReqDto query(@RequestBody promptDto prompt) throws Exception {

        BodyValidator bodyValidator = new BodyValidator();
        bodyValidator.validate(prompt);

        CompletableFuture<llmReqDto> response = promptService.query(RequestMethod.POST, prompt);
        llmReqDto result = response.get();

        return result;
    }

    @PostMapping("/history")
    public String getHistory(@RequestBody String entity) {

        return entity;
    }

}
