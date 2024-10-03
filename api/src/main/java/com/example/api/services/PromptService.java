package com.example.api.services;

import com.example.api.dtos.promptDto;
import com.example.api.repository.PromptRepository;
import com.example.api.repository.UserRepository;
import com.example.api.services.external.llmService;

import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import io.jsonwebtoken.Claims;

@Service
public class PromptService {

    @Autowired
    private llmService llmService;

    @Autowired
    private PromptRepository promptRepository;

    @Autowired
    private UserRepository userRepository;

    private final List<String> queryRes = new ArrayList<>();

    @Async("dbAsyncExecutor")
    public CompletableFuture<Flux<String>> query(HttpMethod method, promptDto prompt, Claims tokenContent) {
        Flux<String> llmRes = llmService.query(method, prompt);

        llmRes
                .doOnNext(data -> {
                    queryRes.add(data);
                })
                .doOnComplete(() -> {
                    String text = String.join("", queryRes);
                    UUID uuid = UUID.randomUUID();
                    UUID userUuid = userRepository.getUuidByEmail(tokenContent.get("email", String.class));

                    promptRepository.addPrompt(uuid, userUuid, text);

                    queryRes.clear();
                }).subscribe();

        return CompletableFuture.completedFuture(llmRes);
    }
}
