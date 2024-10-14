package com.hades.api.services;

import reactor.core.publisher.Flux;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hades.api.dtos.promptDto;
import com.hades.api.repository.PromptRepository;
import com.hades.api.services.external.llmService;

import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PromptService {

    @Autowired
    private llmService llmService;

    @Autowired
    private PromptRepository promptRepository;

    private final List<String> queryRes = new ArrayList<>();

    @Async("dbAsyncExecutor")
    public CompletableFuture<Flux<String>> query(promptDto prompt, String userUuid) {
        Flux<String> llmRes = llmService.query(prompt);

        llmRes
                .doOnNext(data -> {
                    queryRes.add(data);
                })
                .doOnComplete(() -> {
                    String text = String.join("", queryRes);
                    UUID uuid = UUID.randomUUID();
                    UUID userUuidFromString = UUID.fromString(userUuid);

                    promptRepository.addPrompt(uuid, userUuidFromString, text);

                    queryRes.clear();
                }).subscribe();

        return CompletableFuture.completedFuture(llmRes);
    }
}
