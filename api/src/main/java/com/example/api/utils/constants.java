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
    public static final String USERID_CAN_NOT_EMPTY = "userId can not be empty";
    public static final String USERID_LENGTH_INVALID = "userId length must be 16";
    public static final String PROMPT_CAN_NOT_EMPTY = "prompt can not be empty";
    public static final String PROMPT_LENGTH_INVALID = "prompt length must be between 1 and 512";
}
