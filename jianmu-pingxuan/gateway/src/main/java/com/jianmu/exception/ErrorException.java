package com.jianmu.exception;

/**
 * @author lime
 */
public class ErrorException extends RuntimeException {
    public ErrorException(String message) {
        super(message);
    }

    public ErrorException(String message, Throwable cause) {
        super(message, cause);
    }
}
