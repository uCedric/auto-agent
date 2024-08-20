package com.example.api.dtos;

public class dataDto {
    public String response;
    public long time;

    public String getResponse() {
        return this.response;
    }

    public long getTime() {
        return this.time;
    }

    public void setRespose(String response) {
        this.response = response;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
