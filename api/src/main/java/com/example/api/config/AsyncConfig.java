package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "dbAsyncExecutor")
    public ThreadPoolTaskExecutor dbAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("DbAsyncThread-");
        // executor.setRejectedExecutionHandler((r, executor1) -> log.warn("Task
        // rejected, thread pool is full and queue is also full"));
        executor.initialize();
        return executor;
    }

    @Bean(name = "AsyncProcessorExecutor")
    public ThreadPoolTaskExecutor AsyncProcessorExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncProcessorThread-");
        // executor.setRejectedExecutionHandler((r, executor1) -> log.warn("Task
        // rejected, thread pool is full and queue is also full"));
        executor.initialize();
        return executor;
    }
}
