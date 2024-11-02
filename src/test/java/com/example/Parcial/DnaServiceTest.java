package com.example.Parcial;

import com.example.Parcial.entities.Dna;
import com.example.Parcial.repositories.DnaRepository;
import com.example.Parcial.services.DnaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DnaServiceTest {

    @InjectMocks
    private DnaService dnaService;

    @Mock
    private DnaRepository dnaRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testArrayVacio() {
        String[] dna = {};
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> dnaService.esMutante(dna));
        assertEquals("No se permite un array vacío.", exception.getMessage());
    }

    @Test
    void testArrayNxM() {
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

    @Test
    void testEsMutanteDnaNuevo() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};
        when(dnaRepository.findByDna(any(String.class))).thenReturn(Optional.empty());

        boolean result = dnaService.esMutante(dna);

        assertTrue(result);
        verify(dnaRepository, times(1)).save(any(Dna.class));
    }

    @Test
    void testEsMutanteDnaExistente() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"};

        Dna existingDna = new Dna();
        existingDna.setDna(Arrays.toString(dna));
        existingDna.setMutant(true);
        when(dnaRepository.findByDna(any(String.class))).thenReturn(Optional.of(existingDna));

        boolean result = dnaService.esMutante(dna);

        assertTrue(result);
        verify(dnaRepository, never()).save(any(Dna.class));
    }

    @Test
    void testEsHumanoDnaNuevo() {
        String[] dna = {"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"};
        when(dnaRepository.findByDna(any(String.class))).thenReturn(Optional.empty());

        boolean result = dnaService.esMutante(dna);

        assertFalse(result);
        verify(dnaRepository, times(1)).save(any(Dna.class));
    }

    @Test
    void testGetEstadistica() {
        dnaService.esMutante(new String[]{"ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"});
        dnaService.esMutante(new String[]{"ATGCGA", "CAGTGC", "TTATTT", "AGACGG", "GCGTCA", "TCACTG"});

        Map<String, Object> stats = dnaService.getEstadistica();

        assertEquals(1L, stats.get("Contador de mutantes: "));
        assertEquals(1L, stats.get("Contador de humanos: "));
        assertEquals(1.0, stats.get("Promedio"));
    }

    @Test
    void testDeleteDna() throws Exception {
        Long id = 1L;
        when(dnaRepository.existsById(id)).thenReturn(true);

        boolean result = dnaService.delete(id);

        assertTrue(result);
        verify(dnaRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteAllDna() throws Exception {
        boolean result = dnaService.deleteAll();

        assertTrue(result);
        verify(dnaRepository, times(1)).deleteAll();
    }
}
