package com.hades.api.dtos;

import java.util.List;
import java.util.UUID;

public class chunksDto {
    private UUID documentUuid;

    private List<String> chunks;

    public chunksDto(UUID documentUuid, List<String> chunks) {
        this.documentUuid = documentUuid;
        this.chunks = chunks;
    }

    public List<String> getChunks() {
        return this.chunks;
    }

    public UUID getDocumentUuid() {
        return documentUuid;
    }

    public void setChunks(List<String> chunks) {
        this.chunks = chunks;
    }

    public void setDocumentUuid(UUID documentUuid) {
        this.documentUuid = documentUuid;
    }
}
