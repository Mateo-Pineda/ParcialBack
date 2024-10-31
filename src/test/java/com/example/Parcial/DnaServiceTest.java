package com.example.Parcial;

import com.example.Parcial.services.DnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DnaServiceTest {
    private DnaService dnaService;

    @BeforeEach
    void setUp(){
        dnaService = new DnaService();
    }

    @Test
    void testArrayVacio(){
        String[] dna = {};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dnaService.esMutante(dna));
        assertEquals("No se permite un array vacío.", exception.getMessage());
    }

    @Test
    void testArrayNxM(){
        String[] dna = {"ATG", "CAGT", "TTG"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dnaService.esMutante(dna));
        assertEquals("El array debe ser de NxN.", exception.getMessage());
    }

    @Test
    void testArrayConNumeros() {
        String[] dna = {"1234", "5678", "91011", "1213"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dnaService.esMutante(dna));
        assertEquals("El array contiene caracteres inválidos, solo se permite 'A', 'T', 'C', 'G'.", exception.getMessage());
    }

    @Test
    void testArrayNull() {
        String[] dna = null;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dnaService.esMutante(dna));
        assertEquals("No se permite recibir un valor nulo.", exception.getMessage());
    }

    @Test
    void testArrayNxNDeNulls() {
        String[] dna = {null, null, null, null};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dnaService.esMutante(dna));
        assertEquals("El array contiene valores nulos.", exception.getMessage());
    }

    @Test
    void testArrayConLetrasInvalidas() {
        String[] dna = {"BHBG", "CTHB", "ATGT", "CGAA"};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dnaService.esMutante(dna));
        assertEquals("El array contiene caracteres inválidos, solo se permite 'A', 'T', 'C', 'G'.", exception.getMessage());
    }
}
