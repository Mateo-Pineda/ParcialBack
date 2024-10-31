package com.example.Parcial.entities;

import jakarta.persistence.Entity;
import lombok.*;

import java.io.Serializable;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder

public class Dna extends Base implements Serializable {
    private String dna;
    private boolean isMutant;
}
