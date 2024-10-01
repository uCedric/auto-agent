package com.example.api.services.external;

import java.util.concurrent.atomic.AtomicReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import com.example.api.dtos.llmResDto;
import com.example.api.dtos.promptDto;
import com.example.api.utils.httpReqMaker;

@Service
public class llmService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Flux<String> query(HttpMethod method, promptDto prompt) {
        WebClient webClient = webClientBuilder.build();
        AtomicReference<StringBuilder> accumulatedData = new AtomicReference<>(new StringBuilder());

        Flux<String> streamData = webClient.method(method).uri("http://localhost:8081/spring-prompt/query")
                .bodyValue(prompt)
                .retrieve()
                .bodyToFlux(String.class).map(data -> {
                    return data + "\n";
                });

        streamData.doOnNext(data -> {
            // Process and accumulate the data
            accumulatedData.get().append(data).append("\n");
        }).blockLast();

        System.out.println(accumulatedData.get().toString());

        return streamData;
    }
}