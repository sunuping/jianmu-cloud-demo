package com.jianmu.exception;

/**
 * @author lime
 */
public class VoteException extends RuntimeException {
    public VoteException(String message) {
        super(message);
    }

    public VoteException(String message, Throwable cause) {
        super(message, cause);
    }
}
