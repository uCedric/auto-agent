package com.hades.api.utils.Exceptions;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message) {
        super("[Invalid Parameter]:" + message);
    }
}
