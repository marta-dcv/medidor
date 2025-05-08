package com.cebem.medidor.controllers.repositories;

import com.cebem.medidor.models.Movies;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MoviesRepository extends JpaRepository<Movies, Long> {

    List<Movies> findTop10ByOrderByValoracionDesc(); // Top 10 por valoración
}
