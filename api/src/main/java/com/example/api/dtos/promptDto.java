package com.example.api.dtos;

import com.example.api.utils.constants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class promptDto {
    @NotEmpty(message = constants.USERUUID_CAN_NOT_EMPTY)
    @Size(min = 36, max = 36, message = constants.USERUUID_LENGTH_INVALID)
    private String userUuid;
    @NotEmpty(message = constants.PROMPT_CAN_NOT_EMPTY)
    @Size(min = 1, max = 200000000, message = constants.PROMPT_LENGTH_INVALID)
    private String content;

    public String getOwner() {
        return this.userUuid;
    }

    public String getContent() {
        return this.content;
    }

    public void setUserUuid(String userUuid) {
        this.userUuid = userUuid;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
