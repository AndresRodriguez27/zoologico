package com.zoologico.zoo.controller;

import com.zoologico.zoo.entity.Animal;
import com.zoologico.zoo.entity.Especie;
import com.zoologico.zoo.entity.Zona;
import com.zoologico.zoo.security.UserDetailServiceImpl;
import com.zoologico.zoo.service.AnimalService;
import com.zoologico.zoo.service.EspecieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    private AnimalService animalService;

    private EspecieService especieService;

    @PostMapping("/crear")
    public ResponseEntity<String> crearAnimal(@RequestBody Animal animal) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Optional<Animal> existeAnimal = Optional.ofNullable(animalService.findById(animal.getId()));
        if (existeAnimal.isPresent()) {
            return ResponseEntity.badRequest().body("el animal ya existe");
        }

        animalService.save(animal);
        return ResponseEntity.ok("animal creado exitosamente");

    }

    @GetMapping("/listar")
    public ResponseEntity<List<Animal>> listarAnimales() {

        List<Animal> animales = animalService.findAll();

        return ResponseEntity.ok(animales);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarAnimal(@RequestParam("id") Long id) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        animalService.delete(id);
        return ResponseEntity.ok("animals eliminada exitosamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizaranimal(@RequestBody Animal animal) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Animal existeAnimal = animalService.findById(animal.getId());
        if (existeAnimal != null) {
            animalService.update(animal);
            return ResponseEntity.ok("animal actualizado exitosamente");
        } else {
            return ResponseEntity.badRequest().body(" NO existe");
        }

    }

    @PutMapping("/asociarAnimal")
    public ResponseEntity<String> asociarAnimal(@RequestParam("idEspecie") Long idEspecie, @RequestParam("idAnimal") Long idAnimal) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Especie especie = especieService.findById(idEspecie);
        if (especie == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La especie NO existe");
        }
        Animal animal = animalService.findById(idAnimal);
        animal.setEspecie(animal.getEspecie());
        animalService.update(animal);

        return ResponseEntity.ok("Asociado exitosamente");

    }
}
