package com.hades.api.utils.Exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super("[Forbiden]:" + message);
    }

}
