package com.semana7.semana7.services;

import com.semana7.semana7.dto.ConsultaRequestDTO;
import com.semana7.semana7.dto.ConsultaResponseDTO;
import com.semana7.semana7.entidades.Consulta;
import com.semana7.semana7.repositorios.ConsultaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;
    private final NutricionistaService nutricionistaService;
    private final PacienteService pacienteService;

    @Autowired
    public ConsultaService(ConsultaRepository consultaRepository, NutricionistaService nutricionistaService, PacienteService pacienteService) {
        this.consultaRepository = consultaRepository;
        this.nutricionistaService = nutricionistaService;
        this.pacienteService = pacienteService;
    }

    public List<ConsultaResponseDTO> listarConsultas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public ConsultaResponseDTO buscarConsultaPorId(Long id) {
        Optional<Consulta> optionalConsulta = consultaRepository.findById(id);
        return optionalConsulta.map(this::converterParaDTO).orElse(null);
    }

    public ConsultaResponseDTO criarConsulta(ConsultaRequestDTO consultaRequestDTO) {
        Consulta consulta = new Consulta();
        BeanUtils.copyProperties(consultaRequestDTO, consulta);

        // Busca o nutricionista pelo ID fornecido no DTO
        Long nutricionistaId = consultaRequestDTO.getNutricionistaId();
        if (nutricionistaId != null) {
            consulta.setNutricionista(nutricionistaService.buscarNutricionistaPorId(nutricionistaId));
        }

        // Busca o paciente pelo ID fornecido no DTO
        Long pacienteId = consultaRequestDTO.getPacienteId();
        if (pacienteId != null) {
            consulta.setPaciente(pacienteService.buscarPacientePorId(pacienteId));
        }

        Consulta consultaSalva = consultaRepository.save(consulta);
        return converterParaDTO(consultaSalva);
    }

    public ConsultaResponseDTO atualizarConsulta(Long id, ConsultaRequestDTO consultaRequestDTO) {
        Optional<Consulta> optionalConsulta = consultaRepository.findById(id);
        if (optionalConsulta.isPresent()) {
            Consulta consulta = optionalConsulta.get();
            BeanUtils.copyProperties(consultaRequestDTO, consulta);

            // Atualiza o nutricionista se o ID for fornecido no DTO
            Long nutricionistaId = consultaRequestDTO.getNutricionistaId();
            if (nutricionistaId != null) {
                consulta.setNutricionista(nutricionistaService.buscarNutricionistaPorId(nutricionistaId));
            }

            // Atualiza o paciente se o ID for fornecido no DTO
            Long pacienteId = consultaRequestDTO.getPacienteId();
            if (pacienteId != null) {
                consulta.setPaciente(pacienteService.buscarPacientePorId(pacienteId));
            }

            Consulta consultaAtualizada = consultaRepository.save(consulta);
            return converterParaDTO(consultaAtualizada);
        }
        return null;
    }

    public boolean deletarConsulta(Long id) {
        Optional<Consulta> optionalConsulta = consultaRepository.findById(id);
        if (optionalConsulta.isPresent()) {
            consultaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ConsultaResponseDTO converterParaDTO(Consulta consulta) {
        ConsultaResponseDTO consultaResponseDTO = new ConsultaResponseDTO();
        BeanUtils.copyProperties(consulta, consultaResponseDTO);
        consultaResponseDTO.setPaciente(pacienteService.converterParaDTO(consulta.getPaciente()));
        consultaResponseDTO.setNutricionista(nutricionistaService.converterParaDTO(consulta.getNutricionista()));
        return consultaResponseDTO;
    }
}
