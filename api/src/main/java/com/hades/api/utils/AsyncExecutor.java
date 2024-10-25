package com.hades.api.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncExecutor<T, R> {
    public static <T, R> AsyncExecutor<T, R> init() {
        return new AsyncExecutor<>();
    }

    @Async("AsyncExecutor")
    public CompletableFuture<Map<String, R>> process(AsyncService<T, R> tasks)
            throws InterruptedException, ExecutionException {
        Thread currentThread = Thread.currentThread();
        System.out.println("executor thread name: " + currentThread.getName());
        List<Function<T, CompletableFuture<R>>> services = tasks.getServices();
        Map<String, T> dtoMap = tasks.getDtoMap();

        Map<String, R> results = new HashMap<>();
        int order = 1;

        for (Function<T, CompletableFuture<R>> service : services) {
            System.out.println(dtoMap.get("task1"));
            CompletableFuture<R> taskResult = service.apply(dtoMap.get("task" + order));
            R result = taskResult.get();

            results.put("task" + order, result);

            order++;
        }

        return CompletableFuture.completedFuture(results);
    }
}
