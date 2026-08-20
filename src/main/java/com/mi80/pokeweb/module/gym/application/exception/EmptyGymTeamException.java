package com.mi80.pokeweb.module.gym.application.exception;

public class EmptyGymTeamException extends RuntimeException {
    public EmptyGymTeamException(String message) {
        super(message);
    }

    public EmptyGymTeamException(String message, Throwable cause) {
        super(message, cause);
    }
}
