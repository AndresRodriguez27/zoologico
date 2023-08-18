package com.zoologico.zoo.service;

import com.zoologico.zoo.Dao.IZonaDao;
import com.zoologico.zoo.entity.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ZonaSerivice {

    @Autowired
    private IZonaDao zonaDao;

    public List<Zona> findAll() {
        return zonaDao.findAll();
    }

    @Transactional
    public void save(Zona zona) { zonaDao.save(zona); }

    public Optional<Zona> findById(Long id) {
        return zonaDao.findById(id);
    }

    public void delete(Long id) { zonaDao.deleteById(id); }

    public Optional<Zona> findByNombre(String nombre) {
        return zonaDao.findByNombre(nombre);
    }

    public void update(Zona zona , long id) { zonaDao.save(zona); }
}
