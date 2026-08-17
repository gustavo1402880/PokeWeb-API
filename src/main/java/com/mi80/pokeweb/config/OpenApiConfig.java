package com.mi80.pokeweb.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

/**
 * Pokémon REST API simulation game
 *
 * <p>An API for the educational and functional simulation of a Pokémon game, based on HTTP request</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@OpenAPIDefinition(
        info = @Info(
                title = "Pokémon REST API simulation game",
                version = "v1.0.0",
                description = "A simple REST API to simulate a Pokémon game"
        )
)
public class OpenApiConfig {
}
