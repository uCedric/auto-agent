package com.example.api.dtos;

import java.util.List;

public class chunksDto {
    private List<String> chunks;

    public chunksDto(List<String> chunks) {
        this.chunks = chunks;
    }

    public List<String> getChunks() {
        return this.chunks;
    }

    public void setChunks(List<String> chunks) {
        this.chunks = chunks;
    }
}
