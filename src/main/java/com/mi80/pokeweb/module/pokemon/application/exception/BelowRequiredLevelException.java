package com.mi80.pokeweb.module.pokemon.application.exception;

public class BelowRequiredLevelException extends RuntimeException {
    public BelowRequiredLevelException(String message) {
        super(message);
    }

    public BelowRequiredLevelException(String message, Throwable cause) {
        super(message, cause);
    }
}
