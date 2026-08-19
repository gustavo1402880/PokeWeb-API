package com.mi80.pokeweb.module.pokemon.application.exception;

public class MaxEvolutionStageException extends RuntimeException {
    public MaxEvolutionStageException(String message) {
        super(message);
    }

    public MaxEvolutionStageException(String message, Throwable cause) {
        super(message, cause);
    }
}
