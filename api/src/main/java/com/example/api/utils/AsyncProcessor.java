package com.example.api.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.concurrent.ExecutionException;
import org.springframework.scheduling.annotation.Async;

public class AsyncProcessor<T, R> {
    private List<Function<T, CompletableFuture<R>>> services = new ArrayList<>();

    private List<T> dtos = new ArrayList<>();

    public static <T, R> AsyncProcessor<T, R> init() {
        AsyncProcessor<T, R> asyncProcessor = new AsyncProcessor<>();
        return asyncProcessor;
    }

    public AsyncProcessor<T, R> addTask(Function<T, CompletableFuture<R>> service, T dto) {
        this.services.add(service);
        this.dtos.add(dto);
        return this;
    }

    @Async("AsyncProcessorExecutor")
    public CompletableFuture<List<R>> process() throws InterruptedException, ExecutionException {
        List<R> results = new ArrayList<>();

        for (Function<T, CompletableFuture<R>> service : services) {
            for (T dto : dtos) {
                CompletableFuture<R> taskResult = service.apply(dto);
                R result = taskResult.get();
                results.add(result);

                continue;
            }
            continue;
        }

        return CompletableFuture.completedFuture(results);
    }
}