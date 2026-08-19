package com.mi80.pokeweb.controller;

import com.mi80.pokeweb.entity.BattleResult;
import com.mi80.pokeweb.entity.Game;
import com.mi80.pokeweb.entity.Pokemon;
import com.mi80.pokeweb.enums.ItemType;
import com.mi80.pokeweb.enums.Trainer;
import com.mi80.pokeweb.service.GameService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1.0.0/game")
@Tag(
        name = "Game HTTP Request Controller",
        description = """
                This controller exposes the endpoints
                related to the Game entity
                """
)
public class GameController {

    private final GameService service;

    public GameController(GameService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Game> create(
            @RequestParam Trainer trainer
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.createNewGame(trainer)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> findById(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findGameById(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/{id}/team")
    public ResponseEntity<List<Pokemon>> findGameTeam(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findGameTeam(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<Map<ItemType, Integer>> findGameInventory(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findGameInventory(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @GetMapping("/{id}/coins")
    public ResponseEntity<Integer> findGameCoins(
            @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.findGameCoins(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .build();
        }
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<Game> moveNextGym(
        @PathVariable UUID id
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.moveToGym(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/{id}/capture/{pokemonId}")
    public ResponseEntity<Game> capturePokemon(
            @PathVariable UUID id,
            @PathVariable UUID pokemonId
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.capturePokemon(id, pokemonId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Game> buyItem(
            @PathVariable UUID id,
            @RequestParam ItemType item
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.buyItem(id, item)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PatchMapping("/{id}/teams/{pokemonId}/items")
    public ResponseEntity<Pokemon> useItem(
        @PathVariable UUID id,
        @PathVariable UUID pokemonId,
        @RequestParam ItemType itemType
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.useItem(
                            id,
                            pokemonId,
                            itemType
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PatchMapping("/{id}/teams/{pokemonId}/heal")
    public ResponseEntity<Pokemon> heal(
            @PathVariable UUID id,
            @PathVariable UUID pokemonId
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.heal(id, pokemonId)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @PostMapping("/battles/{firstPokemonId}/{secondPokemonId}")
    public ResponseEntity<BattleResult> startBattle(
            @PathVariable UUID firstPokemonId,
            @PathVariable UUID secondPokemonId
    ) {
        try {
            return ResponseEntity.ok().body(
                    service.startBattle(
                            firstPokemonId,
                            secondPokemonId
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
    }

    @PostMapping("/{id}/battles/gyms")
    public ResponseEntity<List<BattleResult>> challengeGymLeader(
            @PathVariable UUID id
    ) {

        try {
            return ResponseEntity.ok().body(
                    service.challengeGymLeader(id)
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .build();
        }
    }
}
