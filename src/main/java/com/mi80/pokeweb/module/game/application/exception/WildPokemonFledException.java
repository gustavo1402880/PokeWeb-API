package com.mi80.pokeweb.module.game.application.exception;

public class WildPokemonFledException extends RuntimeException {
    public WildPokemonFledException(String message) {
        super(message);
    }

    public WildPokemonFledException(String message, Throwable cause) {
        super(message, cause);
    }
}
