package com.semana7.semana7.controllers;

import com.semana7.semana7.dto.ConsultaRequestDTO;
import com.semana7.semana7.dto.ConsultaResponseDTO;
import com.semana7.semana7.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaService consultaService;

    @Autowired
    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultas() {
        List<ConsultaResponseDTO> consultas = consultaService.listarConsultas();
        return ResponseEntity.ok(consultas);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> buscarConsultaPorId(@PathVariable Long id) {
        ConsultaResponseDTO consulta = consultaService.buscarConsultaPorId(id);
        return consulta != null ? ResponseEntity.ok(consulta) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> criarConsulta(@RequestBody ConsultaRequestDTO consultaRequestDTO) {
        ConsultaResponseDTO consultaSalva = consultaService.criarConsulta(consultaRequestDTO);
        return new ResponseEntity<>(consultaSalva, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaResponseDTO> atualizarConsulta(@PathVariable Long id, @RequestBody ConsultaRequestDTO consultaRequestDTO) {
        ConsultaResponseDTO consultaAtualizada = consultaService.atualizarConsulta(id, consultaRequestDTO);
        return consultaAtualizada != null ? ResponseEntity.ok(consultaAtualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarConsulta(@PathVariable Long id) {
        boolean deletada = consultaService.deletarConsulta(id);
        return deletada ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
