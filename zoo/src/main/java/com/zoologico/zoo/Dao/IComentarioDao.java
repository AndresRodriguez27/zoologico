package com.zoologico.zoo.Dao;

import com.zoologico.zoo.entity.Comentario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IComentarioDao extends JpaRepository<Comentario, Long> {
}
