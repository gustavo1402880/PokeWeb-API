package com.mi80.pokeweb.module.game.application.exception;

public class InsufficientCoinsException extends RuntimeException {
    public InsufficientCoinsException(String message) {
        super(message);
    }

    public InsufficientCoinsException(String message, Throwable cause) {
        super(message, cause);
    }
}
