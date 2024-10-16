package com.hades.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {

    @Bean(name = "TaskThread")
    public ThreadPoolTaskExecutor dbAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("TaskThread-");
        // executor.setRejectedExecutionHandler((r, executor1) -> log.warn("Task
        // rejected, thread pool is full and queue is also full"));
        executor.initialize();
        return executor;
    }

    @Bean(name = "AsyncExecutor")
    public ThreadPoolTaskExecutor AsyncProcessorExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(3);
        executor.setMaxPoolSize(3);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("AsyncExecutor-");
        // executor.setRejectedExecutionHandler((r, executor1) -> log.warn("Task
        // rejected, thread pool is full and queue is also full"));
        executor.initialize();
        return executor;
    }
}
