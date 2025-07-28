package com.dominik.control.kidshieldbackend.exception;

public class RefreshTokenAlreadyRevokedException extends KidShieldException{
    public RefreshTokenAlreadyRevokedException() {
    }

    public RefreshTokenAlreadyRevokedException(String message) {
        super(message);
    }

    public RefreshTokenAlreadyRevokedException(String message, Throwable cause) {
        super(message, cause);
    }

    public RefreshTokenAlreadyRevokedException(Throwable cause) {
        super(cause);
    }

    public RefreshTokenAlreadyRevokedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
