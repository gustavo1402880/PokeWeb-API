package com.mi80.pokeweb.controller;

import com.mi80.pokeweb.entity.AttackResult;
import com.mi80.pokeweb.entity.Pokemon;
import com.mi80.pokeweb.service.PokemonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        return ResponseEntity.ok(service.findAll());
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
        return ResponseEntity.ok().body(
                service.findById(id)
        );
    }

    /**
     * Attack another Pokémon
     *
     * <p>Records the attack between an attacking
     * Pokémon and a defender, returning the attack result</p>
     *
     * @param attackerId Pokémon attacker ID
     * @param defenderId Pokémon defender ID
     * @return A {@link ResponseEntity} containing an object of {@link AttackResult}
     * and the HTTP 200 (OK) status
     */
    @Operation(
            summary = "Attack another Pokémon",
            description = """
                    Records the attack between an attacking
                    Pokemon and a defender, returning the attack result
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Successful attack"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Attack was not carried out"
            )
    })
    @PostMapping("{attackerId}/attack/{defenderId}")
    public ResponseEntity<AttackResult> attack(
            @Parameter(
                    name = "attackerId",
                    description = "Attacker unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID attackerId,
            @Parameter(
                    name = "defenderId",
                    description = "Defender unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID defenderId
    ) {
        return ResponseEntity.ok().body(
                service.attack(attackerId, defenderId)
        );
    }

    @GetMapping("{defenderId}/dodge/{attackerId}")
    public ResponseEntity<Map<String, Boolean>> dodge(
            @Parameter(
                    name = "defenderId",
                    description = "Defender unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID defenderId,
            @Parameter(
                    name = "attackerId",
                    description = "Attacker unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID attackerId
    ) {
        return ResponseEntity.ok().body(
                Map.of("success",
                        service.dodge(
                                defenderId,
                                attackerId
                        )
                )
        );
    }

    @GetMapping("{pokemonId}/flee/{opponentId}")
    public ResponseEntity<Map<String, Boolean>> flee(
            @Parameter(
                    name = "pokemonId",
                    description = "Pokemon unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID pokemonId,
            @Parameter(
                    name = "opponentId",
                    description = "Opponent unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID opponentId
    ) {
        return ResponseEntity.ok().body(
                Map.of("success",
                        service.flee(
                                pokemonId,
                                opponentId
                        )
                )
        );
    }

    @PatchMapping("{pokemonId}/level-up")
    public ResponseEntity<Pokemon> levelUp(
            @Parameter(
                    name = "pokemonId",
                    description = "Pokemon unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID pokemonId
    ) {
        return ResponseEntity.ok().body(
                service.levelUp(pokemonId)
        );
    }

    @PutMapping("{pokemonId}/evolve")
    public ResponseEntity<Pokemon> evolve(
            @Parameter(
                    name = "pokemonId",
                    description = "Pokemon unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID pokemonId,
            @Parameter(
                    name = "pokemonEvolved",
                    description = "Pokemon evolved form object",
                    required = true
            )
            @RequestBody Pokemon pokemonEvolved
    ) {
        return ResponseEntity.ok().body(
                service.evolve(
                        pokemonId,
                        pokemonEvolved
                )
        );
    }

    @PatchMapping("{pokemonId}/move")
    public ResponseEntity<Pokemon> movePosition(
            @Parameter(
                    name = "pokemonId",
                    description = "Pokemon unique identification",
                    required = true,
                    example = "c5d9c438-a364-42a5-a6cb-f4f0bf4a4ff1"
            )
            @PathVariable UUID pokemonId
    ) {
        return ResponseEntity.ok().body(
                service.movePosition(pokemonId)
        );
    }
}
