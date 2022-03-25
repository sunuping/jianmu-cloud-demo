package com.jianmu.exception;

/**
 * @author lime
 */
public class TokenExpireException extends RuntimeException {
    public TokenExpireException(String message) {
        super(message);
    }

    public TokenExpireException(String message, Throwable cause) {
        super(message, cause);
    }
}
