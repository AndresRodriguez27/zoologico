package com.zoologico.zoo.service;

import com.zoologico.zoo.Dao.IAnimalDao;
import com.zoologico.zoo.entity.Animal;
import com.zoologico.zoo.entity.Zona;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AnimalService {

    @Autowired
    private IAnimalDao animalDao;

    public List<Animal> findAll() {
        // TODO Auto-generated method stub
        return animalDao.findAll();
    }

    @Transactional
    public void save(Animal animal) {
        animalDao.save(animal);

    }

    public Animal findById(Long id) {
        return animalDao.findById(id).orElse(null);
    }

    public void delete(Long id) {
        // TODO Auto-generated method stub

    }

    public void update(Animal animal) {
    }
}
