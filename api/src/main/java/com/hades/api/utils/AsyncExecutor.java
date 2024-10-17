package com.hades.api.utils;

import com.hades.api.dtos.Dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class AsyncExecutor {

    @Async("AsyncExecutor")
    public CompletableFuture<Map<String, String>> process(AsyncService<Dto, String> tasks)
            throws InterruptedException, ExecutionException {
        Thread currentThread = Thread.currentThread();
        System.out.println("executor thread name: " + currentThread.getName());
        List<Function<Dto, CompletableFuture<String>>> services = tasks.getServices();
        Map<String, Dto> dtoMap = tasks.getDtoMap();

        Map<String, String> results = new HashMap<>();
        int order = 1;

        for (Function<Dto, CompletableFuture<String>> service : services) {
            System.out.println(dtoMap.get("task1"));
            CompletableFuture<String> taskResult = service.apply(dtoMap.get("task" + order));
            String result = taskResult.get();

            results.put("task" + order, result);

            order++;
        }

        return CompletableFuture.completedFuture(results);
    }
}
