package com.example.api.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import com.example.api.dtos.promptDto;

@Service
public class llmService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Flux<String> query(HttpMethod method, promptDto prompt) {
        WebClient webClient = webClientBuilder.build();

        Flux<String> streamData = webClient.method(method).uri("http://localhost:8081/spring-prompt/query")
                .bodyValue(prompt)
                .retrieve()
                .bodyToFlux(String.class).map(data -> {
                    return data + "\n";
                });

        return streamData;
    }
}