package com.hades.api.utils.Exceptions;

public class ExternalServerException extends RuntimeException {
    public ExternalServerException(String message) {
        super("[External Server Error]:" + message);
    }
}
