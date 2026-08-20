package com.mi80.pokeweb.module.gym.infrastructure.controller;

import com.mi80.pokeweb.module.gym.application.exception.EmptyGymTeamException;
import com.mi80.pokeweb.module.gym.application.exception.GymNotFoundException;
import com.mi80.pokeweb.module.gym.core.entity.Gym;
import com.mi80.pokeweb.module.pokemon.core.entity.Pokemon;
import com.mi80.pokeweb.module.gym.application.service.GymService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1.0.0/gyms")
@Tag(
        name = "Gym HTTP Request Controller",
        description = """
                This controller exposes the endpoints
                related to the Gym entity
                """
)
public class GymController {

    private final GymService service;

    public GymController(GymService service) {
        this.service = service;
    }

    /**
     * Find all Gyms
     *
     * <p>Searches for and return all
     * Gyms registered in the system</p>
     *
     * @return A {@link ResponseEntity} containing a list of {@link Gym} objects
     * and the HTTP 200 (OK) status
     */
    @Operation(
            summary = "Find all Gym",
            description = """
                    Searches for and return all
                    Gyms registered in the system
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "All registered Gyms were found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No Gym was found"
            )
    })
    @GetMapping
    public ResponseEntity<List<Gym>> listAll() {
        try {
            return ResponseEntity.ok().body(
                    service.listAll()
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    /**
     * Find Gym by ID
     *
     * <p>Searches for and returns the
     * Gym with the corresponding ID</p>
     *
     * @return A {@link ResponseEntity} containing an object {@link Gym}
     * and the HTTP 200 (OK) status
     */
    @Operation(
            summary = "Find Gym by ID",
            description = """
                    Searches for and returns the
                    Gym with the corresponding ID
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "All registered Gyms were found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No Gym was found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Gym> findById(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findById(id)
            );
        } catch (GymNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }


    /**
     * Find Gym Pokémon Team
     *
     * <p>Searches for and returns the
     * Gym Pokémon Team</p>
     *
     * @return A {@link ResponseEntity} containing an object {@link Pokemon}
     * and the HTTP 200 (OK) status
     */
    @Operation(
            summary = "Find Gym Pokémon Team",
            description = """
                    Searches for and returns the
                    Gym Pokémon Team
                    """
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "All registered Gyms were found"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "No Gym was found"
            ),
            @ApiResponse(
                    responseCode = "422",
                    description = "No Pokemon Team was found"
            )
    })
    @GetMapping("/{id}/pokemons")
    public ResponseEntity<List<Pokemon>> findGymPokemonTeam(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findPokemon(id)
            );
        } catch (GymNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        } catch (EmptyGymTeamException e) {
            return ResponseEntity
                    .status(HttpStatus.UNPROCESSABLE_CONTENT)
                    .build();
        }
    }
}
