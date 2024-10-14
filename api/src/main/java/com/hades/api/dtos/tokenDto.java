package com.hades.api.dtos;

import java.util.HashMap;
import java.util.Map;

public class tokenDto {
    private Map<String, String> token = new HashMap<>();

    public tokenDto(String token) {
        this.token.put("token", token);
    }

    public Map<String, String> getToken() {
        return this.token;
    }
}
