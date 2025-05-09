package com.cebem.medidor.services;

import com.cebem.medidor.models.Superhero;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class SuperheroService {

    private static final String TOKEN = "REMOVED_TOKEN";
    private static final String BASE_URL = "https://superheroapi.com/api/" + TOKEN + "/";

    private final RestTemplate restTemplate = new RestTemplate();

    // Método para obtener todos los superhéroes
    public Map<String, List<Superhero>> getSuperheroesGroupedByAffiliation() {
        Map<String, List<Superhero>> groupedByAffiliation = new HashMap<>();

        for (int i = 1; i <= 100; i++) {
            try {
                Superhero hero = restTemplate.getForObject(BASE_URL + i, Superhero.class);

                if (hero != null && hero.getConnections() != null) {
                    String affiliations = hero.getConnections().getGroupAffiliation();
                    if (affiliations != null && !affiliations.trim().isEmpty()) {
                        String[] groups = affiliations.split(",|;");
                        for (String group : groups) {
                            String trimmedGroup = group.trim();
                            if (!trimmedGroup.isEmpty()) {
                                groupedByAffiliation
                                    .computeIfAbsent(trimmedGroup, k -> new ArrayList<>())
                                    .add(hero);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                System.err.println("Error al obtener héroe ID " + i + ": " + e.getMessage());
            }
        }

        return groupedByAffiliation;
    }

    // Método para obtener una batalla aleatoria entre dos héroes
    public Map<String, Object> getRandomHeroBattle() {
        // Obtener todos los héroes
        List<Superhero> allHeroes = new ArrayList<>();
        for (List<Superhero> heroes : getSuperheroesGroupedByAffiliation().values()) {
            allHeroes.addAll(heroes);
        }

        // Seleccionar dos héroes aleatorios
        Collections.shuffle(allHeroes);
        Superhero hero1 = allHeroes.get(0);
        Superhero hero2 = allHeroes.get(1);

        // Determinar el ganador
        String winner = compareHeroes(hero1, hero2);

        // Crear la estructura para la batalla
        Map<String, Object> battleResult = new HashMap<>();
        battleResult.put("hero1", hero1);
        battleResult.put("hero2", hero2);
        battleResult.put("winner", winner);

        return battleResult;
    }

    // Método para comparar los poderes de dos héroes y determinar un ganador
    private String compareHeroes(Superhero hero1, Superhero hero2) {
        int power1 = hero1.getPowerstats() != null ? Integer.parseInt(hero1.getPowerstats().getPower()) : 0;
        int power2 = hero2.getPowerstats() != null ? Integer.parseInt(hero2.getPowerstats().getPower()) : 0;

        if (power1 > power2) {
            return hero1.getName();
        } else if (power2 > power1) {
            return hero2.getName();
        } else {
            return "Draw";  // En caso de empate
        }
    }
}
