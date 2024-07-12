package com.semana7.semana7.services;

import com.semana7.semana7.dto.PacienteRequestDTO;
import com.semana7.semana7.dto.PacienteResponseDTO;
import com.semana7.semana7.entidades.Paciente;
import com.semana7.semana7.repositorios.PacienteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PacienteService {

    private final PacienteRepository pacienteRepository;

    @Autowired
    public PacienteService(PacienteRepository pacienteRepository) {
        this.pacienteRepository = pacienteRepository;
    }

    public List<PacienteResponseDTO> listarPacientes() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        return pacientes.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public PacienteResponseDTO buscarPacientePorId(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        return optionalPaciente.map(this::converterParaDTO).orElse(null);
    }

    public PacienteResponseDTO criarPaciente(PacienteRequestDTO pacienteRequestDTO) {
        Paciente paciente = new Paciente();
        BeanUtils.copyProperties(pacienteRequestDTO, paciente);

        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        return converterParaDTO(pacienteSalvo);
    }

    public PacienteResponseDTO atualizarPaciente(Long id, PacienteRequestDTO pacienteRequestDTO) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if (optionalPaciente.isPresent()) {
            Paciente paciente = optionalPaciente.get();
            BeanUtils.copyProperties(pacienteRequestDTO, paciente);

            Paciente pacienteAtualizado = pacienteRepository.save(paciente);
            return converterParaDTO(pacienteAtualizado);
        }
        return null;
    }

    public boolean deletarPaciente(Long id) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findById(id);
        if (optionalPaciente.isPresent()) {
            pacienteRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private PacienteResponseDTO converterParaDTO(Paciente paciente) {
        PacienteResponseDTO pacienteResponseDTO = new PacienteResponseDTO();
        BeanUtils.copyProperties(paciente, pacienteResponseDTO);
        return pacienteResponseDTO;
    }
}
