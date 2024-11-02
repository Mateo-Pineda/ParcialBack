package com.example.Parcial.services;

import com.example.Parcial.repositories.DnaRepository;
import com.example.Parcial.entities.Dna;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Service
public class DnaService {

    @Autowired
    private DnaRepository dnaRepository;

    private long contadorMutantes = 0;
    private long contadorHumanos = 0;

    private static final int letras_iguales = 4;

    public boolean esMutante(String[] dna){

        validarDna(dna);
        String dnaString = Arrays.toString(dna);

        if (dnaRepository.findByDna(dnaString).isPresent()){
            return dnaRepository.findByDna(dnaString).get().isMutant();
        }

        boolean isMutant = confirmarMutante(dna);

        Dna dnaMutante = new Dna();
        dnaMutante.setDna(dnaString);
        dnaMutante.setMutant(isMutant);
        dnaRepository.save(dnaMutante);

        if (isMutant){
            contadorMutantes++;
        } else {
            contadorHumanos++;
        }
        return isMutant;
    }

    private void validarDna(String[] dna) {
        if (dna == null){
            throw new IllegalArgumentException("No se permite recibir un valor nulo.");
        }
        if (dna.length == 0){
            throw new IllegalArgumentException("No se permite un array vacío.");
        }
        int n = dna.length;
        for (String row : dna){
            if (row == null){
                throw new IllegalArgumentException("El array contiene valores nulos.");
            }
            if (row.length() != n){
                throw new IllegalArgumentException("El array debe ser de NxN.");
            }
            if (!row.matches("^[ATCG]+$")){
                throw new IllegalArgumentException("El array contiene caracteres inválidos, solo se permite 'A', 'T', 'C', 'G'.");
            }
        }
    }

    public Map<String, Object> getEstadistica(){
        double promedio = contadorHumanos> 0 ? (double) contadorMutantes / contadorHumanos: 0.0;
        Map<String, Object> estadistica = new HashMap<>();
        estadistica.put("Contador de mutantes: ", contadorMutantes);
        estadistica.put("Contador de humanos: ", contadorHumanos);
        estadistica.put("Promedio", promedio);
        return estadistica;
    }

    private boolean confirmarMutante(String[] dna){
            int length = dna.length;
            int secuenciasEncontradas = 0;

            for (int row = 0; row < length; row++) {
                for (int col = 0; col < length; col++) {
                    if (col <= length - letras_iguales && verificaHorizontal(dna, row, col)) {
                        secuenciasEncontradas++;
                    }
                    if (row <= length - letras_iguales && verificaVertical(dna, row, col)) {
                        secuenciasEncontradas++;
                    }
                    if (row <= length - letras_iguales && col <= length - letras_iguales && verificaDiagonalDerecha(dna, row, col)) {
                        secuenciasEncontradas++;
                    }
                    if (row <= length - letras_iguales && col >= letras_iguales - 1 && verificaDiagonalIzquierda(dna, row, col)) {
                        secuenciasEncontradas++;
                    }

                    // Si se encuentran más de una secuencia, confirmamos que es mutante y detenemos la búsqueda
                    if (secuenciasEncontradas > 1) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean verificaHorizontal(String[] dna, int row, int col) {
            char inicial = dna[row].charAt(col);
            for (int i = 1; i < letras_iguales; i++) {
                if (dna[row].charAt(col + i) != inicial) {
                    return false;
                }
            }
            return true;
        }

        private boolean verificaVertical(String[] dna, int row, int col) {
            char inicial = dna[row].charAt(col);
            for (int i = 1; i < letras_iguales; i++) {
                if (dna[row + i].charAt(col) != inicial) {
                    return false;
                }
            }
            return true;
        }

        private boolean verificaDiagonalDerecha(String[] dna, int row, int col) {
            char inicial = dna[row].charAt(col);
            for (int i = 1; i < letras_iguales; i++) {
                if (dna[row + i].charAt(col + i) != inicial) {
                    return false;
                }
            }
            return true;
        }

        private boolean verificaDiagonalIzquierda(String[] dna, int row, int col) {
            char inicial = dna[row].charAt(col);
            for (int i = 1; i < letras_iguales; i++) {
                if (dna[row + i].charAt(col - i) != inicial) {
                    return false;
                }
            }
            return true;
        }
        public boolean delete(Long id) throws Exception{
            try{
                if (dnaRepository.existsById(id)){
                    dnaRepository.deleteById(id);
                    return true;
                }else{
                    throw new Exception();
                }
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
        public boolean deleteAll() throws Exception{
            try{
                dnaRepository.deleteAll();
                return true;
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
        }
    }

