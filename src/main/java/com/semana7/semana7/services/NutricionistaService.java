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

    public static Nutricionista buscarNutricionistaPorId(Long id) {
        Optional<Nutricionista> optionalNutricionista = nutricionistaRepository.findById(id);
        return optionalNutricionista.map(this::converterParaDTO).orElse(null);
    }

    public NutricionistaResponseDTO criarNutricionista(NutricionistaRequestDTO nutricionistaRequestDTO) {
        Nutricionista nutricionista = new Nutricionista();
        BeanUtils.copyProperties(nutricionistaRequestDTO, nutricionista);
        Nutricionista nutricionistaSalvo = nutricionistaRepository.save(nutricionista);
        return converterParaDTO(nutricionistaSalvo);
    }

    public NutricionistaResponseDTO atualizarNutricionista(Long id, NutricionistaRequestDTO nutricionistaRequestDTO) {
        Optional<Nutricionista> optionalNutricionista = nutricionistaRepository.findById(id);
        if (optionalNutricionista.isPresent()) {
            Nutricionista nutricionista = optionalNutricionista.get();
            BeanUtils.copyProperties(nutricionistaRequestDTO, nutricionista);
            Nutricionista nutricionistaAtualizado = nutricionistaRepository.save(nutricionista);
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

    NutricionistaResponseDTO converterParaDTO(Nutricionista nutricionista) {
        NutricionistaResponseDTO nutricionistaResponseDTO = new NutricionistaResponseDTO();
        BeanUtils.copyProperties(nutricionista, nutricionistaResponseDTO);
        return nutricionistaResponseDTO;
    }
}
