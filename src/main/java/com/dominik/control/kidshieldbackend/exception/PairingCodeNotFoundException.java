package com.dominik.control.kidshieldbackend.exception;

public class PairingCodeNotFoundException extends KidShieldException{
    public PairingCodeNotFoundException() {
    }

    public PairingCodeNotFoundException(String message) {
        super(message);
    }

    public PairingCodeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public PairingCodeNotFoundException(Throwable cause) {
        super(cause);
    }

    public PairingCodeNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
