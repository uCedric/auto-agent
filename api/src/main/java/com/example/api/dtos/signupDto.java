package com.example.api.dtos;

import com.example.api.utils.constants;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class signupDto {

    @NotEmpty(message = constants.USER_NAME_CAN_NOT_EMPTY)
    @Size(min = 1, max = 30, message = constants.USER_NAME_LENGTH_INVALID)
    private String name;

    @NotEmpty(message = constants.USER_EMAIL_CAN_NOT_EMPTY)
    @NotBlank(message = constants.USER_EMAIL_CAN_NOT_CONTAIN_BLANK)
    @Email(message = constants.USER_EMAIL_INVALID_FORMATE)
    private String email;

    @NotEmpty(message = constants.USER_PASSWORD_CAN_NOT_EMPTY)
    @NotBlank(message = constants.USER_PASSWORD_CAN_NOT_CONTAIN_BLANK)
    @Size(min = 8, max = 30, message = constants.USER_PASSWORD_LENGTH_INVALID)
    private String password;

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return this.name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }
}
