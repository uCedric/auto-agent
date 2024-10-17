package com.hades.api.services.external;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.hades.api.dtos.chunksDto;
import com.hades.api.dtos.promptDto;
import com.hades.api.utils.constants;
import com.hades.api.utils.Exceptions.InternalServerException;

import reactor.core.publisher.Flux;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;

@Service
public class llmService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    public Flux<String> query(promptDto prompt) {
        WebClient webClient = webClientBuilder.build();

        return webClient.post().uri(constants.LLM_API_LOCAL_QUERY).bodyValue(prompt)
                .retrieve()
                .bodyToFlux(String.class);
    }

    public String addChunks(UUID documentUuid, List<String> chunks) {
        WebClient webClient = webClientBuilder.build();

        chunksDto chunksDto = new chunksDto(documentUuid, chunks);

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