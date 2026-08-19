package com.mi80.pokeweb.module.pokemon.application.exception;

public class SamePokemonException extends RuntimeException {
    public SamePokemonException(String message) {
        super(message);
    }

    public SamePokemonException(String message, Throwable cause) {
        super(message, cause);
    }
}
