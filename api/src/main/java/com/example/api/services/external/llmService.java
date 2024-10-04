package com.example.api.services.external;

import com.example.api.dtos.chunksDto;
import com.example.api.dtos.promptDto;
import com.example.api.utils.constants;
import com.example.api.utils.Exceptions.InternalServerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import java.util.List;

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

    public String addChunks(List<String> chunks) {
        WebClient webClient = webClientBuilder.build();

        chunksDto chunksDto = new chunksDto(chunks);

        String result = "";
        try {
            result = webClient.post().uri(constants.LLM_API_LOCAL_ADDCHUNKS)
                    .header("Content-Type", "application/json")
                    .bodyValue(chunksDto)
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();
        } catch (Exception e) {
            throw new InternalServerException("upload file faild");
        }

        return result;
    }
}