package com.semana7.semana7.entidades;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime dataHora;

    @ManyToOne
    @JoinColumn(name = "nutricionista_id")
    private Nutricionista nutricionista;

    @ManyToOne
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    public void setDataHora(LocalDateTime dataHora) {
        this.dataHora = dataHora;
    }

    public Nutricionista getNutricionista() {
        return nutricionista;
    }

    public void setNutricionista(Nutricionista nutricionista) {
        this.nutricionista = nutricionista;
    }

    public Long getNutricionistaId() {
        return nutricionista != null ? nutricionista.getId() : null;
    }

    public void setNutricionistaId(Long nutricionistaId) {
        if (nutricionistaId != null) {
            this.nutricionista = new Nutricionista();
            this.nutricionista.setId(nutricionistaId);
        }
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Long getPacienteId() {
        return paciente != null ? paciente.getId() : null;
    }

    public void setPacienteId(Long pacienteId) {
        if (pacienteId != null) {
            this.paciente = new Paciente();
            this.paciente.setId(pacienteId);
        }
    }
}
