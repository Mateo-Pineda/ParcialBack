package com.example.Parcial.controllers;

import com.example.Parcial.services.DnaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/mutant")
public class DnaController {

    @Autowired
    private DnaService dnaService;

    @PostMapping()
    public ResponseEntity<?> isMutant(@RequestBody Map<String, String[]> dna){
        boolean result = dnaService.esMutante(dna.get("dna"));
        return result ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @GetMapping("/estadistica")
    public ResponseEntity<Map<String, Object>> getEstadistica(){
        return ResponseEntity.ok(dnaService.getEstadistica());
    }
}
