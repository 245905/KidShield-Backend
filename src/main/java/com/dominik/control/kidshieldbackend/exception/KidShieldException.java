package com.dominik.control.kidshieldbackend.exception;

public class KidShieldException extends RuntimeException{
    public KidShieldException() {
    }

    public KidShieldException(String message) {
        super(message);
    }

    public KidShieldException(String message, Throwable cause) {
        super(message, cause);
    }

    public KidShieldException(Throwable cause) {
        super(cause);
    }

    public KidShieldException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
