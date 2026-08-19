package com.mi80.pokeweb.module.pokemon.application.exception;

public class PokemonNotFoundException extends RuntimeException {
    public PokemonNotFoundException(String message) {
        super(message);
    }

    public PokemonNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
