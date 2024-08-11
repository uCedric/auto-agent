package com.example.api.pojos;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Prompt {
    private String id;

    @NotEmpty(message = "userId is required")
    @Size(min = 16, max = 16, message = "userId length must be 16")
    private String userId;
    @NotEmpty(message = "prompt is required")
    @Size(min = 1, max = 512, message = "prompt length must be between 1 and 512")
    private String prompt;

    public String getOwner() {
        return this.userId;
    }

    public String getPrompt() {
        return this.prompt;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
