package com.zoologico.zoo.Dao;

import com.zoologico.zoo.entity.Especie;
import com.zoologico.zoo.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IEspecieDao extends JpaRepository<Especie, Long> {
    Optional<Especie> findById(Long id);

    Optional<Especie> findByNombre(String nombre);

    Optional<Especie> findByNombreAndZona(String nombre, Zona zona);
}
