package com.cebem.medidor.controllers;

import com.cebem.medidor.services.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SuperheroController {

    @Autowired
    private SuperheroService superheroService;

    @GetMapping("/superheroes")
    public String showSuperheroes(Model model) {
        model.addAttribute("groupBattles", superheroService.getRandomGroupBattles(3));
        return "SuperheroCard";
    }
}
