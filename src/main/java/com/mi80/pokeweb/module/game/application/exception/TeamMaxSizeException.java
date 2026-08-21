package com.mi80.pokeweb.module.game.application.exception;

public class TeamMaxSizeException extends RuntimeException {
    public TeamMaxSizeException(String message) {
        super(message);
    }

    public TeamMaxSizeException(String message, Throwable cause) {
        super(message, cause);
    }
}
