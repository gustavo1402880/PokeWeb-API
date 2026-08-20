package com.mi80.pokeweb.module.gym.application.exception;

public class GymNotFoundException extends RuntimeException {
    public GymNotFoundException(String message) {
        super(message);
    }

    public GymNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
