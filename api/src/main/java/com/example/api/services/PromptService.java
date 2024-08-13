package com.example.api.services;

import com.example.api.pojos.Prompt;
import com.example.api.services.external.llmService;
import com.example.api.utils.dataFormate;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class PromptService {

    @Async
    public String query(Prompt prompt) {
        llmService llmService = new llmService();
        String jasonfiedPrompt = dataFormate.jasonfy("prompt", prompt.toString());
        String response = llmService.query(jasonfiedPrompt);
        // db insert

        return response;
    }
}
