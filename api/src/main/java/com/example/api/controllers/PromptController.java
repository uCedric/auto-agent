package com.example.api.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.example.api.dtos.dataDto;
import com.example.api.dtos.llmResDto;
import com.example.api.dtos.promptDto;
import com.example.api.services.PromptService;
import com.example.api.utils.SuccessResponse;
import com.example.api.validators.AuthValidator;
import com.example.api.validators.BodyValidator;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletableFuture;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMethod;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/prompts")
public class PromptController {

    @Autowired
    private PromptService promptService;

    @Autowired
    private AuthValidator authValidator;

    @Autowired
    private BodyValidator bodyValidator;

    @Autowired
    private WebClient.Builder webClientBuilder;

    // @PostMapping("/query")
    // public SuccessResponse<dataDto> query(@RequestHeader("authorization") String
    // token, @RequestBody promptDto prompt)
    // throws Exception {
    // Claims tokenContent = authValidator.validateToken(token);
    // // tokenContent.get("role", String.class);
    // bodyValidator.validate(prompt);

    // CompletableFuture<llmResDto> response =
    // promptService.query(RequestMethod.POST, prompt);
    // llmResDto result = response.get();

    // SuccessResponse<dataDto> successResponse = new SuccessResponse<>(200,
    // "Success", result.getData());

    // return successResponse;
    // }

    @PostMapping("/query")
    public CompletableFuture<Flux<String>> query(@RequestBody promptDto prompt) throws Exception {
        CompletableFuture<Flux<String>> result = promptService.query(HttpMethod.POST, prompt);

        return result;
    }

    @PostMapping("/history")
    public String getHistory(@RequestBody String entity) {

        return entity;
    }

}
