package com.mi80.pokeweb.module.pokemon.infrastructure.controller;

import com.mi80.pokeweb.module.game.application.service.result.AttackResult;
import com.mi80.pokeweb.module.pokemon.application.exception.BelowRequiredLevelException;
import com.mi80.pokeweb.module.pokemon.application.exception.FaintedPokemonException;
import com.mi80.pokeweb.module.pokemon.application.exception.PokemonNotFoundException;
import com.mi80.pokeweb.module.pokemon.application.exception.SamePokemonException;
import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import com.mi80.pokeweb.module.pokemon.application.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Pokémon HTTP Request Controller
 *
 * <p>This controller exposes the endpoints
 * related to the Pokémon entity</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1.0.0/pokemons")
@Tag(
        name = "Pokémon HTTP Request Controller",
        description = """
                This controller exposes the endpoints
                related to the Pokémon entity
                """
)
public class PokemonController {

    private final PokemonService service;

    public PokemonController(PokemonService service) {
        this.service = service;
    }

    /**
     * Find all Pokémon
     *
     * <p>Searches for and return all
     * Pokémon registered in the system</p>
     *
     * @return A {@link ResponseEntity} containing a list of {@link Pokemon} objects
     * and the HTTP 200 (OK) status
     */
    @Operation(
            summary = "Find all Pokémon",
            description = """
                    Searches for and return all
                    Pokémon registered in the system
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "All registered Pokémon were found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No Pokémon was found"
            )
    })
    @GetMapping
    public ResponseEntity<List<Pokemon>> findAll() {
        try {
            return ResponseEntity.ok(service.findAll());
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    /**
     * Find Pokémon by ID
     *
     * <p>Searches for and returns the
     * Pokémon with the corresponding ID</p>
     *
     * @return A {@link ResponseEntity} containing an object of {@link Pokemon}
     * and the HTTP 200 (OK) status
     */
    @Operation(
            summary = "Find Pokémon by ID",
            description = """
                    Searches for and returns the
                    Pokémon with the corresponding ID
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Pokemon was found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Pokémon was not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> findById(
            @Parameter(
                    name = "id",
                    description = "Pokemon unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findById(id)
            );
        } catch (PokemonNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
