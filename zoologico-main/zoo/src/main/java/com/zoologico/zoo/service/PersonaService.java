package com.zoologico.zoo.service;

import com.zoologico.zoo.Dao.IPersonaDao;
import com.zoologico.zoo.entity.Persona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PersonaService {

    @Autowired
    private IPersonaDao personaDao;

    public List<Persona> findAll() {
        return personaDao.findAll();
    }

    @Transactional
    public void save(Persona persona) {
        personaDao.save(persona);
    }

    public Persona findById(Long id) {
        return personaDao.findById(id).orElse(null);
    }

        public void delete(Long id) {
            // TODO Auto-generated method stub

        }
}
