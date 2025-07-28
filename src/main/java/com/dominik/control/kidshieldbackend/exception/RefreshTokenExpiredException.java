package com.dominik.control.kidshieldbackend.exception;

public class RefreshTokenExpiredException extends KidShieldException{
    public RefreshTokenExpiredException() {
    }

    public RefreshTokenExpiredException(String message) {
        super(message);
    }

    public RefreshTokenExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenExpiredException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
