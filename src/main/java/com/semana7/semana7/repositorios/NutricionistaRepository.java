package com.semana7.semana7.repositorios;

import com.semana7.semana7.entidades.Nutricionista;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NutricionistaRepository extends JpaRepository<Nutricionista, Long> {
    boolean existsByNome(String nome);
}