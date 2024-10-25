package com.hades.api.utils.Exceptions;

public class UnauthorizedException extends RuntimeException {
    public UnauthorizedException(String message) {
        super("[Unauthorized]: " + message);
    }
}
