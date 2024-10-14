package com.hades.api.dtos;

import java.util.HashMap;
import java.util.Map;

import com.hades.api.utils.constants;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class signupDto extends Dto {
    @NotEmpty(message = constants.USER_EMAIL_CAN_NOT_EMPTY)
    @NotBlank(message = constants.USER_EMAIL_CAN_NOT_CONTAIN_BLANK)
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = constants.USER_EMAIL_INVALID_FORMATE)
    private String email;

    @NotEmpty(message = constants.USER_PASSWORD_CAN_NOT_EMPTY)
    @NotBlank(message = constants.USER_PASSWORD_CAN_NOT_CONTAIN_BLANK)
    @Size(min = 8, max = 30, message = constants.USER_PASSWORD_LENGTH_INVALID)
    private String password;

    @NotEmpty(message = constants.USER_NAME_CAN_NOT_EMPTY)
    @Size(min = 1, max = 30, message = constants.USER_NAME_LENGTH_INVALID)
    private String name;

    public signupDto(String name, String email, String password) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public String getPassword() {
        return this.password;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public Map<String, Object> getAttributes() {
        Map<String, Object> attributes = new HashMap<>();
        attributes.put("email", this.getEmail());
        attributes.put("password", this.getPassword());
        attributes.put("name", this.name);

        return attributes;
    }
}
