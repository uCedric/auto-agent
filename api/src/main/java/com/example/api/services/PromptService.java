package com.example.api.services;

import com.example.api.dtos.dataDto;
import com.example.api.dtos.llmResDto;
import com.example.api.dtos.promptDto;
import com.example.api.repository.PromptRepository;
import com.example.api.services.external.llmService;
import com.example.api.utils.dataFormate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PromptService {

    @Autowired
    private PromptRepository promptRepository;

    @Async("dbAsyncExecutor")
    public CompletableFuture<llmResDto> query(RequestMethod method, promptDto prompt) {
        try {
            UUID uuid = UUID.fromString(prompt.getOwner());
            String jasonfiedPrompt = dataFormate.jasonfy("prompt", prompt.getContent());

            llmService llmService = new llmService();
            llmResDto llmRes = llmService.query(method, jasonfiedPrompt);
            System.out.println("llmRes: " + llmRes.getData().getResponse());

            // TODO: save response to db

            return CompletableFuture.completedFuture(llmRes);
        } catch (Exception e) {
            throw new RuntimeException("Failed to query prompt:", e);
        }
    }
}
