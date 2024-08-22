package com.example.api.utils.Exceptions;

public class DataFormateException extends RuntimeException {
    public DataFormateException(String message) {
        super("[api server]:" + message);
    }
}
