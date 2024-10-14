package com.hades.api.dtos;

import com.hades.api.utils.constants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class promptDto {
    @NotEmpty(message = constants.PROMPT_CAN_NOT_EMPTY)
    @Size(min = 1, max = 200000000, message = constants.PROMPT_LENGTH_INVALID)
    private String content;

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
