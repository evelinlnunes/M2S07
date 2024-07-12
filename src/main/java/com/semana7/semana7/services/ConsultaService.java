package com.semana7.semana7.services;

import com.semana7.semana7.dto.ConsultaRequestDTO;
import com.semana7.semana7.dto.ConsultaResponseDTO;
import com.semana7.semana7.entidades.Consulta;
import com.semana7.semana7.entidades.Nutricionista;
import com.semana7.semana7.entidades.Paciente;
import com.semana7.semana7.repositorios.ConsultaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        // Busca o nutricionista pelo ID e associa à consulta
        Nutricionista nutricionista = nutricionistaService.buscarNutricionistaPorId(consultaRequestDTO.getNutricionistaId());
        consulta.setNutricionista(nutricionista);

        // Busca o paciente pelo ID e associa à consulta
        Paciente paciente = pacienteService.buscarPacientePorId(consultaRequestDTO.getPacienteId());
        consulta.setPaciente(paciente);

        Consulta consultaSalva = consultaRepository.save(consulta);
        return converterParaDTO(consultaSalva);
    }

    public ConsultaResponseDTO atualizarConsulta(Long id, ConsultaRequestDTO consultaRequestDTO) {
        Optional<Consulta> optionalConsulta = consultaRepository.findById(id);
        if (optionalConsulta.isPresent()) {
            Consulta consulta = optionalConsulta.get();
            BeanUtils.copyProperties(consultaRequestDTO, consulta);

            // Busca o nutricionista pelo ID e associa à consulta
            Nutricionista nutricionista = nutricionistaService.buscarNutricionistaPorId(consultaRequestDTO.getNutricionistaId());
            consulta.setNutricionista(nutricionista);

            // Busca o paciente pelo ID e associa à consulta
            Paciente paciente = pacienteService.buscarPacientePorId(consultaRequestDTO.getPacienteId());
            consulta.setPaciente(paciente);

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

    public List<ConsultaResponseDTO> listarConsultasComInformacoes() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream()
                .map(this::converterParaDTOComInformacoes)
                .collect(Collectors.toList());
    }

    private ConsultaResponseDTO converterParaDTO(Consulta consulta) {
        ConsultaResponseDTO consultaResponseDTO = new ConsultaResponseDTO();
        BeanUtils.copyProperties(consulta, consultaResponseDTO);
        return consultaResponseDTO;
    }

    private ConsultaResponseDTO converterParaDTOComInformacoes(Consulta consulta) {
        ConsultaResponseDTO consultaResponseDTO = new ConsultaResponseDTO();
        consultaResponseDTO.setDataHora(consulta.getDataHora());

        // Verifica se o nutricionista não é nulo antes de acessar o nome
        if (consulta.getNutricionista() != null) {
            consultaResponseDTO.setNomeNutricionista(consulta.getNutricionista().getNome());
        }

        // Verifica se o paciente não é nulo antes de acessar o nome
        if (consulta.getPaciente() != null) {
            consultaResponseDTO.setNomePaciente(consulta.getPaciente().getNome());
        }

        return consultaResponseDTO;
    }
}
