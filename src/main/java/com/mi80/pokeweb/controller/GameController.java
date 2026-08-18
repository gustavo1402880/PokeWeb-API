package com.mi80.pokeweb.controller;

import com.mi80.pokeweb.entity.BattleResult;
import com.mi80.pokeweb.entity.Game;
import com.mi80.pokeweb.entity.Gym;
import com.mi80.pokeweb.entity.Pokemon;
import com.mi80.pokeweb.enums.ItemType;
import com.mi80.pokeweb.enums.Trainer;
import com.mi80.pokeweb.service.GameService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
        return ResponseEntity.ok().body(
                service.createNewGame(trainer)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<Game> listAll(
            @PathVariable UUID id
            ) {
        return ResponseEntity.ok().body(
                service.findGameById(id)
        );
    }

    @GetMapping("/{id}/team")
    public ResponseEntity<List<Pokemon>> findGameTeam(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(
                service.findGameTeam(id)
        );
    }

    @GetMapping("/{id}/inventory")
    public ResponseEntity<Map<ItemType, Integer>> findGameInventory(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(
                service.findGameInventory(id)
        );
    }

    @GetMapping("/{id}/coins")
    public ResponseEntity<Integer> findGameCoins(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(
                service.findGameCoins(id)
        );
    }

    @PatchMapping("/{id}/move")
    public ResponseEntity<Game> moveNextGym(
        @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(
                service.moveToGym(id)
        );
    }

    @PostMapping("/{id}/capture/{pokemonId}")
    public ResponseEntity<Game> capturePokemon(
            @PathVariable UUID id,
            @PathVariable UUID pokemonId
    ) {
        return ResponseEntity.ok().body(
                service.capturePokemon(id, pokemonId)
        );
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<Game> buyItem(
            @PathVariable UUID id,
            @RequestParam ItemType item
    ) {
        return ResponseEntity.ok().body(
                service.buyItem(id, item)
        );
    }

    @PatchMapping("/{id}/teams/{pokemonId}/items")
    public ResponseEntity<Pokemon> useItem(
        @PathVariable UUID id,
        @PathVariable UUID pokemonId,
        @RequestParam ItemType itemType
    ) {
        return ResponseEntity.ok().body(
                service.useItem(
                        id,
                        pokemonId,
                        itemType
                )
        );
    }

    @PatchMapping("/{id}/teams/{pokemonId}/heal")
    public ResponseEntity<Pokemon> heal(
            @PathVariable UUID id,
            @PathVariable UUID pokemonId
    ) {
        return ResponseEntity.ok().body(
                service.heal(id, pokemonId)
        );
    }

    @PostMapping("/battles/{firstPokemonId}/{secondPokemonId}")
    public ResponseEntity<BattleResult> startBattle(
            @PathVariable UUID firstPokemonId,
            @PathVariable UUID secondPokemonId
    ) {
        return ResponseEntity.ok().body(
                service.startBattle(
                        firstPokemonId,
                        secondPokemonId
                )
        );
    }

    @PostMapping("/{id}/battles/gyms")
    public ResponseEntity<List<BattleResult>> challengeGymLeader(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok().body(
                service.challengeGymLeader(id)
        );
    }
}
