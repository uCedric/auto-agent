package com.example.api.services;

import com.example.api.dtos.promptDto;
import com.example.api.services.external.llmService;

import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;

@Service
public class PromptService {

    @Autowired
    private llmService llmService;

    @Async("dbAsyncExecutor")
    public CompletableFuture<Flux<String>> query(HttpMethod method, promptDto prompt) {
        Flux<String> llmRes = llmService.query(method, prompt);

        return CompletableFuture.completedFuture(llmRes);
    }
}
