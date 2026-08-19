package com.mi80.pokeweb.controller;

import com.mi80.pokeweb.entity.Gym;
import com.mi80.pokeweb.entity.Pokemon;
import com.mi80.pokeweb.service.GymService;
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

    @GetMapping("/{id}")
    public ResponseEntity<Gym> findById(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findById(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/{id}/pokemons")
    public ResponseEntity<List<Pokemon>> findGymPokemonTeam(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findPokemon(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }
}
