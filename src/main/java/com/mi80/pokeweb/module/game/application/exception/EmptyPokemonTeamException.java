package com.mi80.pokeweb.module.game.application.exception;

public class EmptyPokemonTeamException extends RuntimeException {
    public EmptyPokemonTeamException(String message) {
        super(message);
    }

    public EmptyPokemonTeamException(String message, Throwable cause) {
        super(message, cause);
    }
}
