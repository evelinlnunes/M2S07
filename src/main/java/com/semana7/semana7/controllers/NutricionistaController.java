package com.semana7.semana7.controllers;

import com.semana7.semana7.dto.NutricionistaRequestDTO;
import com.semana7.semana7.dto.NutricionistaResponseDTO;
import com.semana7.semana7.services.NutricionistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nutricionistas")
public class NutricionistaController {

    private final NutricionistaService nutricionistaService;

    @Autowired
    public NutricionistaController(NutricionistaService nutricionistaService) {
        this.nutricionistaService = nutricionistaService;
    }

    @GetMapping
    public ResponseEntity<List<NutricionistaResponseDTO>> listarNutricionistas() {
        List<NutricionistaResponseDTO> nutricionistas = nutricionistaService.listarNutricionistas();
        return ResponseEntity.ok(nutricionistas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<NutricionistaResponseDTO> buscarNutricionistaPorId(@PathVariable Long id) {
        NutricionistaResponseDTO nutricionista = nutricionistaService.buscarNutricionistaPorId(id);
        return nutricionista != null ? ResponseEntity.ok(nutricionista) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<NutricionistaResponseDTO> criarNutricionista(@RequestBody NutricionistaRequestDTO nutricionistaRequestDTO) {
        NutricionistaResponseDTO nutricionistaSalvo = nutricionistaService.criarNutricionista(nutricionistaRequestDTO);
        return new ResponseEntity<>(nutricionistaSalvo, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<NutricionistaResponseDTO> atualizarNutricionista(@PathVariable Long id, @RequestBody NutricionistaRequestDTO nutricionistaRequestDTO) {
        NutricionistaResponseDTO nutricionistaAtualizado = nutricionistaService.atualizarNutricionista(id, nutricionistaRequestDTO);
        return nutricionistaAtualizado != null ? ResponseEntity.ok(nutricionistaAtualizado) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarNutricionista(@PathVariable Long id) {
        boolean deletado = nutricionistaService.deletarNutricionista(id);
        return deletado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
