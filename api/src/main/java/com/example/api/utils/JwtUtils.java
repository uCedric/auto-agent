package com.example.api.utils;

import org.springframework.beans.factory.annotation.Value;

public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;
}
