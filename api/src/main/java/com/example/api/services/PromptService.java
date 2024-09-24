package com.example.api.services;

import com.example.api.dtos.llmResDto;
import com.example.api.dtos.promptDto;
import com.example.api.repository.PromptRepository;
import com.example.api.services.external.llmService;
import com.example.api.utils.dataFormate;
import com.example.api.utils.Exceptions.InternalServerException;

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
        UUID uuid = UUID.randomUUID();
        UUID userUuid = UUID.fromString(prompt.getOwner());
        String jasonfiedPrompt = dataFormate.jasonfy("prompt", prompt.getContent());

        llmService llmService = new llmService();
        llmResDto llmRes = llmService.query(method, jasonfiedPrompt);
        String content = llmRes.getData().getResponse();

        int dbModifiedLine = promptRepository.addPrompt(uuid, userUuid, content);

        if (dbModifiedLine == 0) {
            throw new InternalServerException("Failed to save prompt to db");
        }

        return CompletableFuture.completedFuture(llmRes);
    }
}
