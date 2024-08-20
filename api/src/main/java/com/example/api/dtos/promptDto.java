package com.example.api.dtos;

import com.example.api.utils.constants;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class promptDto {
    private String id;

    @NotEmpty(message = constants.USERID_CAN_NOT_EMPTY)
    @Size(min = 16, max = 16, message = constants.USERID_LENGTH_INVALID)
    private String userId;
    @NotEmpty(message = constants.PROMPT_CAN_NOT_EMPTY)
    @Size(min = 1, max = 512, message = constants.PROMPT_LENGTH_INVALID)
    private String content;

    public String getOwner() {
        return this.userId;
    }

    public String getContent() {
        return this.content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
