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

                // Asegurarse de que el héroe y su afiliación no sean nulos
                if (hero != null && hero.getConnections() != null) {
                    String affiliations = hero.getConnections().getGroupAffiliation();
                    if (affiliations != null && !affiliations.trim().isEmpty()) {
                        String[] groups = affiliations.split(",|;");
                        for (String group : groups) {
                            String trimmedGroup = group.trim();
                            if (!trimmedGroup.isEmpty()) {
                                // Añadir al mapa de afiliaciones
                                groupedByAffiliation
                                    .computeIfAbsent(trimmedGroup, k -> new ArrayList<>())
                                    .add(hero);
                            }
                        }
                    }
                }

            } catch (Exception e) {
                // Imprimir el error para depurar
                System.err.println("Error al obtener héroe ID " + i + ": " + e.getMessage());
            }
        }

        return groupedByAffiliation;
    }

    public Map<String, Map<String, List<Superhero>>> getRandomGroupBattles(int count) {
        Map<String, List<Superhero>> allGroups = getSuperheroesGroupedByAffiliation();
        List<String> groupNames = new ArrayList<>(allGroups.keySet());
        Collections.shuffle(groupNames);

        Map<String, Map<String, List<Superhero>>> battles = new LinkedHashMap<>();

        int selected = 0;
        Set<String> used = new HashSet<>();

        // Se seleccionan 2 grupos para enfrentarse
        while (selected < count * 2 && groupNames.size() >= 2) {
            String group1 = groupNames.remove(0);
            String group2 = groupNames.remove(0);

            // Verificar si alguno de los grupos está vacío
            if (allGroups.get(group1).isEmpty() || allGroups.get(group2).isEmpty()) continue;

            battles.put(group1, Map.of(
                "enemies", allGroups.get(group2),
                "allies", allGroups.get(group1)
            ));

            used.add(group1);
            used.add(group2);
            selected += 2;
        }

        return battles;
    }
}
