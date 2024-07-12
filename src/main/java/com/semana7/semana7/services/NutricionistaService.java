package com.semana7.semana7.services;

import com.semana7.semana7.dto.NutricionistaRequestDTO;
import com.semana7.semana7.dto.NutricionistaResponseDTO;
import com.semana7.semana7.entidades.Nutricionista;
import com.semana7.semana7.repositorios.NutricionistaRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NutricionistaService {

    private final NutricionistaRepository nutricionistaRepository;

    @Autowired
    public NutricionistaService(NutricionistaRepository nutricionistaRepository) {
        this.nutricionistaRepository = nutricionistaRepository;
    }

    public List<NutricionistaResponseDTO> listarNutricionistas() {
        List<Nutricionista> nutricionistas = nutricionistaRepository.findAll();
        return nutricionistas.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public NutricionistaResponseDTO buscarNutricionistaPorId(Long id) {
        Optional<Nutricionista> optionalNutricionista = nutricionistaRepository.findById(id);
        return optionalNutricionista.map(this::converterParaDTO).orElse(null);
    }

    public NutricionistaResponseDTO criarNutricionista(NutricionistaRequestDTO nutricionistaRequestDTO) {
        // Verificar se o nome do nutricionista já existe
        if (nutricionistaRepository.existsByNome(nutricionistaRequestDTO.getNome())) {
            throw new RuntimeException("Nome de nutricionista já existe: " + nutricionistaRequestDTO.getNome());
        }

        Nutricionista nutricionista = new Nutricionista();
        BeanUtils.copyProperties(nutricionistaRequestDTO, nutricionista);

        Nutricionista nutricionistaSalvo = nutricionistaRepository.save(nutricionista);
        return converterParaDTO(nutricionistaSalvo);
    }

    public NutricionistaResponseDTO atualizarNutricionista(Long id, NutricionistaRequestDTO nutricionistaRequestDTO) {
        Optional<Nutricionista> optionalNutricionista = nutricionistaRepository.findById(id);
        if (optionalNutricionista.isPresent()) {
            Nutricionista nutricionistaExistente = optionalNutricionista.get();

            // Verificar se o nome está sendo alterado para um nome já existente
            if (!nutricionistaRequestDTO.getNome().equals(nutricionistaExistente.getNome())
                    && nutricionistaRepository.existsByNome(nutricionistaRequestDTO.getNome())) {
                throw new RuntimeException("Nome de nutricionista já existe: " + nutricionistaRequestDTO.getNome());
            }

            BeanUtils.copyProperties(nutricionistaRequestDTO, nutricionistaExistente);
            Nutricionista nutricionistaAtualizado = nutricionistaRepository.save(nutricionistaExistente);
            return converterParaDTO(nutricionistaAtualizado);
        }
        return null;
    }

    public boolean deletarNutricionista(Long id) {
        Optional<Nutricionista> optionalNutricionista = nutricionistaRepository.findById(id);
        if (optionalNutricionista.isPresent()) {
            nutricionistaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public void adicionarAnoExperiencia(Long id) {
        Optional<Nutricionista> optionalNutricionista = nutricionistaRepository.findById(id);
        if (optionalNutricionista.isPresent()) {
            Nutricionista nutricionista = optionalNutricionista.get();
            nutricionista.setTempoDeExperiencia(nutricionista.getTempoDeExperiencia() + 1);
            nutricionistaRepository.save(nutricionista);
        }
    }

    public void adicionarCertificacao(Long id, String certificacao) {
        Optional<Nutricionista> optionalNutricionista = nutricionistaRepository.findById(id);
        if (optionalNutricionista.isPresent()) {
            Nutricionista nutricionista = optionalNutricionista.get();
            nutricionista.setCertificacao(certificacao);
            nutricionistaRepository.save(nutricionista);
        }
    }

    private NutricionistaResponseDTO converterParaDTO(Nutricionista nutricionista) {
        NutricionistaResponseDTO nutricionistaResponseDTO = new NutricionistaResponseDTO();
        BeanUtils.copyProperties(nutricionista, nutricionistaResponseDTO);
        return nutricionistaResponseDTO;
    }
}
