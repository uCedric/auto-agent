package com.hades.api.utils;

public class SuccessResponse<T> {
    private int statusCode;
    private String message;
    private T data;

    public SuccessResponse(int statusCode, String message, T data) {
        this.statusCode = statusCode;
        this.message = message;
        this.data = data;
    }

    public SuccessResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public T getData() {
        return this.data;
    }
}
