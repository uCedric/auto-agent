package com.example.api.services.external;

import com.example.api.dtos.promptDto;
import com.example.api.utils.constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@Service
public class llmService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Flux<String> query(promptDto prompt) {
        WebClient webClient = webClientBuilder.build();

        Flux<String> streamData = webClient.post().uri(constants.LLM_API_LOCAL_QUERY)
                .bodyValue(prompt)
                .retrieve()
                .bodyToFlux(String.class).map(data -> {
                    return data + "\n";
                });

        return streamData;
    }
}