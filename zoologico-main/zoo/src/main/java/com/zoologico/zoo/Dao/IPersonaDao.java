package com.zoologico.zoo.Dao;

import com.zoologico.zoo.entity.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IPersonaDao extends JpaRepository<Persona, Long> {
    Optional<Persona> findByIdentificacion(Long identificacion);
}
