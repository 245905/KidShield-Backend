package com.dominik.control.kidshieldbackend.exception;

public class PairingCodeExpiredException extends KidShieldException{
    public PairingCodeExpiredException() {
    }

    public PairingCodeExpiredException(String message) {
        super(message);
    }

    public PairingCodeExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PairingCodeExpiredException(Throwable cause) {
        super(cause);
    }

    public PairingCodeExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
