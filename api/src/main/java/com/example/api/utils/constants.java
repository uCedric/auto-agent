package com.example.api.utils;

public class constants {

    // external service API
    public static final String LLM_SERVICE_API_LOCAL = "http://localhost:3000/spring-prompt/query";

    // HTTP methods
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_DELETE = "DELETE";
    public static final String HTTP_METHOD_PATCH = "PATCH";

    // validator error messages
    public static final String USERID_CAN_NOT_EMPTY = "userId can not be empty.";
    public static final String USERID_LENGTH_INVALID = "userId length must be 16.";
    public static final String USER_NAME_CAN_NOT_EMPTY = "user name can not be empty.";
    public static final String USER_NAME_LENGTH_INVALID = "user name length must less than 30 chars.";
    public static final String USER_EMAIL_CAN_NOT_EMPTY = "user email can not be empty.";
    public static final String USER_EMAIL_CAN_NOT_CONTAIN_BLANK = "user email can not contain blank.";
    public static final String USER_EMAIL_INVALID_FORMATE = "user email is invalid formate.";
    public static final String USER_PASSWORD_CAN_NOT_EMPTY = "user password can not be empty.";
    public static final String USER_PASSWORD_CAN_NOT_CONTAIN_BLANK = "user password can not contain blank.";
    public static final String USER_PASSWORD_LENGTH_INVALID = "user password length must be between 8 and 30.";
    public static final String PROMPT_CAN_NOT_EMPTY = "prompt can not be empty.";
    public static final String PROMPT_LENGTH_INVALID = "prompt length must be between 1 and 512.";
}
