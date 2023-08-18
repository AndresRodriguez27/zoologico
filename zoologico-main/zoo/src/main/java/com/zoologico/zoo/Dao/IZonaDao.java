package com.zoologico.zoo.Dao;

import com.zoologico.zoo.entity.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IZonaDao extends JpaRepository<Zona, Long> {

    Optional<Zona> findById(Long id);

    Optional<Zona> findByNombre (String nombre);
}
