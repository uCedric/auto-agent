package com.example.api.utils;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpMethod;

/*
    usage:
    httpReqMaker httpReqMaker = new httpReqMaker("header", "body");
    httpReqMaker.setAuth("token");
    httpReqMaker.send("url", "method");
*/

public class httpReqMaker {
    private HttpHeaders httpHeader;
    private String body;

    public httpReqMaker() {
        HttpHeaders httpHeader = new HttpHeaders();
        httpHeader.set("Content-Type", "application/json");

        this.httpHeader = httpHeader;
    }

    public void setAuth(String token) {
        this.httpHeader.set("Authorization", "Bearer " + token);
    }

    public void setBody(String body) {// TODO: customized body structure
        this.body = body;
    }

    public String send(String url, String method) {
        RestTemplate restTemplate = new RestTemplate();
        HttpMethod httpMethod = getHttpMethod(method);

        HttpEntity<String> entity = new HttpEntity<String>(body, httpHeader);
        ResponseEntity<String> response = restTemplate.exchange(url, httpMethod, entity, String.class);

        return response.getBody();
    }

    public HttpMethod getHttpMethod(String method) {
        if (method == "GET") {
            return HttpMethod.GET;
        } else if (method == "POST") {
            return HttpMethod.POST;
        } else if (method == "PUT") {
            return HttpMethod.PUT;
        } else if (method == "DELETE") {
            return HttpMethod.DELETE;
        } else {
            throw new IllegalArgumentException("Invalid method");
        }
    }

    public void setContentType(String type) {
        this.httpHeader.set("Content-Type", type);
    }
}
