package com.semana7.semana7.dto;

import java.time.LocalDate;

public class ConsultaResponseDTO {
    private Long id;
    private NutricionistaResponseDTO nutricionista;
    private PacienteResponseDTO paciente;
    private LocalDate dataDaConsulta;
    private String observacoes;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NutricionistaResponseDTO getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(NutricionistaResponseDTO nutricionista) {
        this.nutricionista = nutricionista;
    }

    public PacienteResponseDTO getPaciente() {
        return paciente;
    }

    public void setPaciente(PacienteResponseDTO paciente) {
        this.paciente = paciente;
    }

    public LocalDate getDataDaConsulta() {
        return dataDaConsulta;
    }

    public void setDataDaConsulta(LocalDate dataDaConsulta) {
        this.dataDaConsulta = dataDaConsulta;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
