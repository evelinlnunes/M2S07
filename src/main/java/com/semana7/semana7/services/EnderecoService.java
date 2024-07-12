package com.semana7.semana7.services;

import com.semana7.semana7.dto.EnderecoRequestDTO;
import com.semana7.semana7.dto.EnderecoResponseDTO;
import com.semana7.semana7.entidades.Endereco;
import com.semana7.semana7.repositorios.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public List<EnderecoResponseDTO> listarEnderecos() {
        List<Endereco> enderecos = enderecoRepository.findAll();
        return enderecos.stream()
                .map(this::converterParaDTO)
                .collect(Collectors.toList());
    }

    public EnderecoResponseDTO buscarEnderecoPorId(Long id) {
        return enderecoRepository.findById(id)
                .map(this::converterParaDTO)
                .orElse(null);
    }

    public EnderecoResponseDTO criarEndereco(EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoRequestDTO, endereco);
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return converterParaDTO(enderecoSalvo);
    }

    public EnderecoResponseDTO atualizarEndereco(Long id, EnderecoRequestDTO enderecoRequestDTO) {
        return enderecoRepository.findById(id)
                .map(endereco -> {
                    BeanUtils.copyProperties(enderecoRequestDTO, endereco);
                    Endereco enderecoAtualizado = enderecoRepository.save(endereco);
                    return converterParaDTO(enderecoAtualizado);
                })
                .orElse(null);
    }

    public boolean deletarEndereco(Long id) {
        return enderecoRepository.findById(id)
                .map(endereco -> {
                    enderecoRepository.deleteById(id);
                    return true;
                })
                .orElse(false);
    }

    private EnderecoResponseDTO converterParaDTO(Endereco endereco) {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        BeanUtils.copyProperties(endereco, enderecoResponseDTO);
        return enderecoResponseDTO;
    }
}
