package com.hades.api.utils.Exceptions;

public class InternalServerException extends RuntimeException {
    public InternalServerException(String message) {
        super("[Internal Server Error]:" + message);
    }
}
