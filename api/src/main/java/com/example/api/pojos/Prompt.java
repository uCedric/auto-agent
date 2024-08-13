package com.example.api.pojos;

import com.example.api.utils.constants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Prompt {
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

    public String toString() {
        return this.content;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
