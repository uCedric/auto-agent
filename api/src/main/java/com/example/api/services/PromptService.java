package com.example.api.services;

import com.example.api.dtos.llmResDto;
import com.example.api.dtos.promptDto;
import com.example.api.repository.PromptRepository;
import com.example.api.services.external.llmService;
import com.example.api.utils.dataFormate;
import com.example.api.utils.httpReqMaker;
import com.example.api.utils.Exceptions.InternalServerException;

import reactor.core.publisher.Flux;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class PromptService {

    @Autowired
    private llmService llmService;

    @Autowired
    private PromptRepository promptRepository;

    @Async("dbAsyncExecutor")
    public CompletableFuture<Flux<String>> query(HttpMethod method, promptDto prompt) {
        Flux<String> llmRes = llmService.query(method, prompt);

        return CompletableFuture.completedFuture(llmRes);
    }

    @Async("dbAsyncExecutor")
    public CompletableFuture<ResponseBodyEmitter> stream(RequestMethod method, promptDto prompt) {
        System.out.println(prompt.getContent());
        String jasonfiedPrompt = dataFormate.jasonfy("prompt", prompt.getContent());

        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<String>(jasonfiedPrompt, httpHeader);

        RestTemplate restTemplate = new RestTemplate();

        // request to llm-api
        ResponseBodyEmitter emitter = new ResponseBodyEmitter();

        // Use CompletableFuture to handle asynchronous processing
        // CompletableFuture.runAsync(() -> {
        try {
            // Make the POST request and get the response as a String
            String response = restTemplate.postForObject(
                    "http://localhost:8081/spring-prompt/query",
                    entity,
                    String.class);
            System.out.print(response);
            // Split the response into parts by newline and send each part
            // response.split("(?<=\\n)|(?=\\n)")
            for (String part : response.split("(?<=\\n)|(?=\\n)")) {
                // System.out.print(part); // Log the part (optional)
                emitter.send(part); // Send each part as it becomes available
            }

            emitter.complete(); // Signal that the response is complete
        } catch (Exception e) {
            emitter.completeWithError(e); // Handle errors by completing with an error
        }
        // });

        return CompletableFuture.completedFuture(emitter);
    }
}
