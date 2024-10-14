package com.hades.api.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class httpResMaker {
    public static JsonNode jasonfyJasonFormate(String jasonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readTree(jasonString);
        } catch (Exception e) {
            return null;
        }

    }
}
