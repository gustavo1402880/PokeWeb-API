package com.mi80.pokeweb.service;

import com.mi80.pokeweb.entity.Gym;
import com.mi80.pokeweb.entity.Pokemon;
import com.mi80.pokeweb.enums.GymLeader;
import com.mi80.pokeweb.enums.Trainer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class GymService {

    private final List<Gym> gyms = new CopyOnWriteArrayList<>();

    public GymService(PokemonService pokemonService) {
        gyms.add(createGym(
                "Pewter Gym", 1,
                GymLeader.BROCK,null,
                List.of(
                        pokemonService.findByDex(74),
                        pokemonService.findByDex(95)
                )
        ));
        gyms.add(createGym(
                "Cerulean Gym", 2,
                GymLeader.MISTY,null,
                List.of(
                        pokemonService.findByDex(120),
                        pokemonService.findByDex(121)
                )
        ));
        gyms.add(createGym(
                "Vermilion Gym", 3,
                GymLeader.LT_SURGE,null,
                List.of(
                        pokemonService.findByDex(100),
                        pokemonService.findByDex(26)
                )
        ));
        gyms.add(createGym(
                "Celadon Gym", 4,
                GymLeader.ERIKA,null,
                List.of(
                        pokemonService.findByDex(114),
                        pokemonService.findByDex(45)
                )
        ));
        gyms.add(createGym(
                "Fuchsia Gym", 5,
                GymLeader.KOGA,null,
                List.of(
                        pokemonService.findByDex(109),
                        pokemonService.findByDex(89)
                )
        ));
        gyms.add(createGym(
                "Saffron Gym", 6,
                GymLeader.SABRINA,null,
                List.of(
                        pokemonService.findByDex(64),
                        pokemonService.findByDex(65)
                )
        ));
        gyms.add(createGym(
                "Cinnabar Gym", 7,
                GymLeader.BLAINE,null,
                List.of(
                        pokemonService.findByDex(58),
                        pokemonService.findByDex(59)
                )
        ));
        gyms.add(createGym(
                "Viridian Gym", 8,
                GymLeader.GIOVANNI,null,
                List.of(
                        pokemonService.findByDex(111),
                        pokemonService.findByDex(34)
                )
        ));
    }

    public List<Gym> listAll() {
        return List.copyOf(gyms);
    }

    public Gym findById(UUID id) {
        return gyms.stream()
                .filter(gym -> gym.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gym was not found"));
    }

    public Gym findByGymOrder(int order) {
        if (order < 0 || order > 7) {
            throw new RuntimeException("Gym was not found");
        }

        return gyms.stream()
                .filter(gym -> gym.getGymOrder() == order)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Gym was not found"));
    }

    public List<Pokemon> findPokemon(UUID id) {
        return List.copyOf(findById(id).getPokemon());
    }

    private static Gym createGym(
            String name,
            int gymOrder,
            GymLeader leader,
            Trainer challenger,
            List<Pokemon> pokemon
    ) {
        return new Gym(
                name,
                gymOrder,
                leader,
                challenger,
                pokemon
        );
    }
}
