package com.example.api.dtos;

import com.example.api.dtos.dataDto;

public class llmReqDto {
    private String message;
    private dataDto data;

    public String getMessage() {
        return this.message;
    }

    public dataDto getData() {
        return this.data;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setData(dataDto data) {
        this.data = data;
    }
}
