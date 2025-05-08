package com.cebem.medidor.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
// import org.springframework.web.client.RestTemplate;

import com.cebem.medidor.models.RickandmortyCharacter;
import com.cebem.medidor.services.RickandmortyService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RickAndMortyController {

 //   private final RestTemplate restTemplate;
    private final RickandmortyService rickandmortyService;
   

    @GetMapping("/rickandmortyweb")
    public String getRandomCharacter(Model model) {
        // Hacer una solicitud a la API externa para obtener un personaje aleatorio
        //String apiUrl = "https://rickandmortyapi.com/api/character/";
        //RickandmortyCharacter character = restTemplate.getForObject(apiUrl + "1", RickandmortyCharacter.class); // Aquí puedes usar otro endpoint para obtener un random
        RickandmortyCharacter character = rickandmortyService.getCharacterRandom();
        // Agregar el personaje al modelo de Thymeleaf
        model.addAttribute("character", character);

        // Devolver el nombre del template
        return "characterCard";
    }
}
