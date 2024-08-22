package com.example.api.services.external;

import org.springframework.web.bind.annotation.RequestMethod;

import com.example.api.dtos.llmReqDto;
import com.example.api.utils.httpReqMaker;

public class llmService {

    public llmReqDto query(RequestMethod method, String prompt) {
        httpReqMaker httpReqMaker = new httpReqMaker();
        httpReqMaker.setBody(prompt);

        return httpReqMaker.send("http://localhost:8081/spring-prompt/query", method.toString());

    }
}