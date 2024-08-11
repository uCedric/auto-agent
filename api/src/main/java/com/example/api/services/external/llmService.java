package com.example.api.services.external;

import com.example.api.utils.httpReqMaker;

public class llmService {
    public String query(String prompt) {
        httpReqMaker httpReqMaker = new httpReqMaker();
        httpReqMaker.setBody(prompt);

        return httpReqMaker.send("http://localhost:3000/spring-prompt/query", "POST");
    }
}