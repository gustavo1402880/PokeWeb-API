package com.mi80.pokeweb.module.game.application.exception;

public class InsufficientItemQuantityException extends RuntimeException {
    public InsufficientItemQuantityException(String message) {
        super(message);
    }

    public InsufficientItemQuantityException(String message, Throwable cause) {
        super(message, cause);
    }
}
