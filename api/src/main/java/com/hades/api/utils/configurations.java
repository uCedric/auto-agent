package com.hades.api.utils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

public class configurations {

    @Configuration
    static class dbConfig {
        @Bean
        public String connect() {
            return "db connection created";
        }
    }
}
