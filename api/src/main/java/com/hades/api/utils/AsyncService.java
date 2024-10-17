package com.hades.api.utils;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class AsyncService<T, R> {
    private List<Function<T, CompletableFuture<R>>> services = new ArrayList<>();

    private Map<String, T> dtoMap = new HashMap<>();

    public static <T, R> AsyncService<T, R> init() {
        AsyncService<T, R> asyncProcessor = new AsyncService<>();
        return asyncProcessor;
    }

    public AsyncService<T, R> addTask(Function<T, CompletableFuture<R>> service, T dto) {
        this.services.add(service);
        this.dtoMap.put("task" + String.valueOf(this.services.size()), dto);

        return this;
    }

    public List<Function<T, CompletableFuture<R>>> getServices() {
        return this.services;
    }

    public Map<String, T> getDtoMap() {
        return this.dtoMap;
    }
}