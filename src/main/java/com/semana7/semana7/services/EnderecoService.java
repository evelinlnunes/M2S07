package com.semana7.semana7.services;

import com.semana7.semana7.dto.EnderecoRequestDTO;
import com.semana7.semana7.dto.EnderecoResponseDTO;
import com.semana7.semana7.entidades.Endereco;
import com.semana7.semana7.repositorios.EnderecoRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;

    @Autowired
    public EnderecoService(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
    }

    public Endereco buscarEnderecoPorId(Long id) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);
        return optionalEndereco.map(this::converterParaDTO).orElse(null);
    }

    public EnderecoResponseDTO criarEndereco(EnderecoRequestDTO enderecoRequestDTO) {
        Endereco endereco = new Endereco();
        BeanUtils.copyProperties(enderecoRequestDTO, endereco);
        Endereco enderecoSalvo = enderecoRepository.save(endereco);
        return converterParaDTO(enderecoSalvo);
    }

    public EnderecoResponseDTO atualizarEndereco(Long id, EnderecoRequestDTO enderecoRequestDTO) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);
        if (optionalEndereco.isPresent()) {
            Endereco endereco = optionalEndereco.get();
            BeanUtils.copyProperties(enderecoRequestDTO, endereco);
            Endereco enderecoAtualizado = enderecoRepository.save(endereco);
            return converterParaDTO(enderecoAtualizado);
        }
        return null;
    }

    public boolean deletarEndereco(Long id) {
        Optional<Endereco> optionalEndereco = enderecoRepository.findById(id);
        if (optionalEndereco.isPresent()) {
            enderecoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public EnderecoResponseDTO converterParaDTO(Endereco endereco) {
        EnderecoResponseDTO enderecoResponseDTO = new EnderecoResponseDTO();
        BeanUtils.copyProperties(endereco, enderecoResponseDTO);
        return enderecoResponseDTO;
    }
}