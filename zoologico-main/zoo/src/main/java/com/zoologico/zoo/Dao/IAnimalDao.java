package com.zoologico.zoo.Dao;

import com.zoologico.zoo.entity.Animal;
import com.zoologico.zoo.entity.Especie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IAnimalDao extends JpaRepository<Animal, Long> {
    Optional<Animal> findById(Long id);

    Optional<Animal> findByNombreAndEspecie(String nombre, Especie especie);
}
