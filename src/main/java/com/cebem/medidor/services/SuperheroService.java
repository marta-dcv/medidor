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

    public Map<String, Object> getRandomHeroBattle() {
        List<Superhero> allHeroes = new ArrayList<>();
        for (List<Superhero> heroes : getSuperheroesGroupedByAffiliation().values()) {
            allHeroes.addAll(heroes);
        }

        if (allHeroes.size() < 2) {
            throw new IllegalStateException("No hay suficientes superhéroes para una batalla.");
        }

        Collections.shuffle(allHeroes);
        Superhero hero1 = allHeroes.get(0);
        Superhero hero2 = allHeroes.get(1);

        String winner = compareHeroes(hero1, hero2);

        Map<String, Object> battleResult = new HashMap<>();
        battleResult.put("hero1", hero1);
        battleResult.put("hero2", hero2);
        battleResult.put("winner", winner);

        return battleResult;
    }

    private String compareHeroes(Superhero hero1, Superhero hero2) {
        int power1 = extractPower(hero1);
        int power2 = extractPower(hero2);

        if (power1 > power2) return hero1.getName();
        if (power2 > power1) return hero2.getName();
        return "Draw";
    }

    private int extractPower(Superhero hero) {
        try {
            if (hero != null && hero.getPowerstats() != null) {
                String power = hero.getPowerstats().getPower();
                return power != null ? Integer.parseInt(power) : 0;
            }
        } catch (NumberFormatException e) {
            System.err.println("Valor de 'power' no numérico para " + hero.getName());
        }
        return 0;
    }
}
