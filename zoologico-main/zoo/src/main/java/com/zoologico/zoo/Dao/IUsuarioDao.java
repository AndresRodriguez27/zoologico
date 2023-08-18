package com.zoologico.zoo.Dao;

import com.zoologico.zoo.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
@Repository
public interface IUsuarioDao extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
