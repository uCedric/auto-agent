package com.example.api.services;

import com.example.api.services.external.llmService;
import com.example.api.utils.dataFormate;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

    @Async
    public String query(String prompt) {
        // TODO: external service call
        // call llm api
        llmService llmService = new llmService();
        String jasonfiedPrompt = dataFormate.jasonfy("prompt", prompt);
        String result = llmService.query(jasonfiedPrompt);

        return result;
    }
}
