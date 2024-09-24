package com.example.api.dtos;

public class llmResDto {
    private int statusCode;
    private String message;
    private dataDto data;

    public int getStatusCode() {
        return this.statusCode;
    }

    public String getMessage() {
        return this.message;
    }

    public dataDto getData() {
        return this.data;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(dataDto data) {
        this.data = data;
    }
}
