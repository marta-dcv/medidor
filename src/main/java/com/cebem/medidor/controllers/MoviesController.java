package com.cebem.medidor.controllers;

import com.cebem.medidor.models.Movies;
import com.cebem.medidor.controllers.repositories.MoviesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MoviesController {

    @Autowired
    private MoviesRepository moviesRepository;

@PostMapping("/saveMovie")
   @ResponseStatus(HttpStatus.CREATED)
   public void saveSensorData(@RequestBody Movies movie) {
    //detrás del save hay un 'insert into'
       moviesRepository.save(movie); // Guarda los datos en la base de datos
   }



    // GET /movies/getMovies → obtener todas las películas
    @GetMapping("/getMovies")
    public ResponseEntity<List<Movies>> getAllMovies() {
        List<Movies> moviesList = moviesRepository.findAll();
        return ResponseEntity.ok(moviesList);
    }

    // DELETE /movies/deleteMovie/{id} → eliminar película por ID
    @DeleteMapping("/deleteMovie/{id}")
    public ResponseEntity<String> deleteMovieById(@PathVariable Long id) {
        if (!moviesRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        moviesRepository.deleteById(id);
        return ResponseEntity.ok("Película eliminada con éxito");
    }

    // GET /movies/top10Movies → obtener las 10 películas mejor valoradas
    @GetMapping("/top10Movies")
    public ResponseEntity<List<Movies>> getTop10Movies() {
        List<Movies> top10 = moviesRepository.findTop10ByOrderByValoracionDesc();
        return ResponseEntity.ok(top10);
    }
}

