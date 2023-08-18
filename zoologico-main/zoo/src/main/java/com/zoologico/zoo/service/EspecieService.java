package com.zoologico.zoo.service;

import com.zoologico.zoo.Dao.IEspecieDao;
import com.zoologico.zoo.entity.Especie;
import com.zoologico.zoo.entity.Usuario;
import com.zoologico.zoo.entity.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EspecieService {

    @Autowired
    private IEspecieDao especieDao;

    public List<Especie> findAll() {
        return especieDao.findAll();
    }

    @Transactional
    public void save(Especie especie) {
        especieDao.save(especie);
    }


    public void delete(Long id) { especieDao.deleteById(id); }

    public Optional<Especie> findByNombre(String nombre) {
        return especieDao.findByNombre(nombre);
    }

    public void update(Especie especie,Long id) { especieDao.save(especie); }

    public Optional<Especie> findByNombreAndZona(String nombre, Zona zona) {
        return especieDao.findByNombreAndZona(nombre, zona);
    }

    public Optional<Especie> findById(Long id) {
        return especieDao.findById(id);
    }

}
