package com.example.api.utils;

public class dataFormate {
    public static String jasonfy(String key, String value) {
        return "{\"" + key + "\": \"" + value + "\"}";
    }
}
