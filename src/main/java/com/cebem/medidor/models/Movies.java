package com.cebem.medidor.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Movies {
    

    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        private String title; // Dirección MAC del sensor (se recibe desde el ESP32)
        private String director; // Director
        private int duration_minutes; // Duración de la película
        private int year;
        private double valoracion; // Valoracion
    
    }
