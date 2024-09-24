package com.example.api.dtos;

import com.example.api.utils.constants;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class signupDto extends userDto {

    @NotEmpty(message = constants.USER_NAME_CAN_NOT_EMPTY)
    @Size(min = 1, max = 30, message = constants.USER_NAME_LENGTH_INVALID)
    private String name;

    public signupDto(String name, String email, String password) {
        super(email, password);
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
