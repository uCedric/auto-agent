package com.hades.api.utils;

public class constants {

    // external service API
    public static final String LLM_API_LOCAL_QUERY = "http://localhost:8081/spring-prompt/query";
    public static final String LLM_API_LOCAL_ADDCHUNKS = "http://localhost:8081/spring-prompt/chunks";

    // HTTP methods
    public static final String HTTP_METHOD_GET = "GET";
    public static final String HTTP_METHOD_POST = "POST";
    public static final String HTTP_METHOD_PUT = "PUT";
    public static final String HTTP_METHOD_DELETE = "DELETE";
    public static final String HTTP_METHOD_PATCH = "PATCH";

    // validator error messages
    public static final String USERUUID_CAN_NOT_EMPTY = "userUuid can not be empty.";
    public static final String USERUUID_LENGTH_INVALID = "userUuid length must be 16.";
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

    public static final int FILE_MAX_SIZE = 10 * 1024 * 1024;
    public static final String FILE_CONTENT_TYPE = "text/plain";
}
