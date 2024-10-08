package com.example.api.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.concurrent.ExecutionException;
import org.springframework.scheduling.annotation.Async;

public class AsyncProcessor<T, R> {
    private List<Function<T, CompletableFuture<R>>> services = new ArrayList<>();

    private Map<String, T> dtoMap = new HashMap<>();

    public static <T, R> AsyncProcessor<T, R> init() {
        AsyncProcessor<T, R> asyncProcessor = new AsyncProcessor<>();
        return asyncProcessor;
    }

    public AsyncProcessor<T, R> addTask(Function<T, CompletableFuture<R>> service, T dto) {
        this.services.add(service);
        this.dtoMap.put("task" + String.valueOf(this.services.size()), dto);

        return this;
    }

    @Async("AsyncProcessorExecutor")
    public CompletableFuture<Map<String, R>> process() throws InterruptedException, ExecutionException {
        Map<String, R> results = new HashMap<>();
        int order = 1;

        for (Function<T, CompletableFuture<R>> service : services) {
            System.out.println(this.dtoMap.get("task1"));
            CompletableFuture<R> taskResult = service.apply(this.dtoMap.get("task" + order));
            R result = taskResult.get();

            results.put("task" + order, result);

            order++;
        }

        return CompletableFuture.completedFuture(results);
    }
}