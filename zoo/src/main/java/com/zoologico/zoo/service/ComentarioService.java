package com.zoologico.zoo.service;

import com.zoologico.zoo.Dao.IComentarioDao;
import com.zoologico.zoo.entity.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private IComentarioDao comentarioDao;

    public List<Comentario> findAll() {
        // TODO Auto-generated method stub
        return comentarioDao.findAll();
    }

    @Transactional
    public void save(Comentario comentario) {
        comentarioDao.save(comentario);

    }

    public Comentario findById(Long id) {
        return comentarioDao.findById(id).orElse(null);
    }

    public void delete(Long id) {
        // TODO Auto-generated method stub

    }
}
