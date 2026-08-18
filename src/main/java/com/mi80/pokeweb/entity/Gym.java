package com.mi80.pokeweb.entity;

import com.mi80.pokeweb.enums.GymLeader;
import com.mi80.pokeweb.enums.PokemonType;
import com.mi80.pokeweb.enums.Trainer;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Gym entity
 *
 * <p>Represents the entity responsible for storing data of a Pokémon gym</p>
 *
 * @author gustavo_pelissari150
 * @version 1.0.0
 */
@Tag(
        name = "Gym entity",
        description = """
                Represents the entity responsible for
                storing data of a pokémon gym
                """
)
public class Gym {
    private UUID id;
    private String name;
    private PokemonType type;
    private GymLeader leader;
    private Trainer challenger;
    private List<Pokemon> pokemon;

    public Gym() {}

    /**
     *
     *
     * @param name
     * @param leader
     * @param challenger
     * @param pokemon
     */
    public Gym(String name,
               GymLeader leader,
               Trainer challenger,
               List<Pokemon> pokemon
    ) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.type = leader.getSpeciality();
        this.leader = leader;
        this.challenger = challenger;
        this.pokemon = new ArrayList<>(pokemon);
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PokemonType getType() {
        return type;
    }

    public void setType(PokemonType type) {
        this.type = type;
    }

    public GymLeader getLeader() {
        return leader;
    }

    public void setLeader(GymLeader leader) {
        this.leader = leader;
    }

    public Trainer getChallenger() {
        return challenger;
    }

    public void setChallenger(Trainer challenger) {
        this.challenger = challenger;
    }

    public List<Pokemon> getPokemon() {
        return pokemon;
    }

    public void setPokemon(List<Pokemon> pokemon) {
        this.pokemon = pokemon;
    }
}
