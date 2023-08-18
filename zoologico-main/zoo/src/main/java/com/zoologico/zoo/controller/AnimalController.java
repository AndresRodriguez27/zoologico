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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/animales")
public class AnimalController {

    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;
    @Autowired
    private AnimalService animalService;

    @Autowired
    private EspecieService especieService;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> crearAnimal(@RequestBody Animal animal) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }

        Optional<Animal> existeAnimal = animalService.findByNombreAndEspecie(animal.getNombre(), animal.getEspecie());

        if (existeAnimal.isPresent()) {
            errorResponse.put("error", "El nombre del animal ya es existente en esta especie");
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            animalService.save(animal);
            response.put("success", "Animal agregado exitosamente");
            return ResponseEntity.ok(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Animal>> listarAnimales() {
        List<Animal> animales = animalService.findAll();
        return ResponseEntity.ok(animales);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminarAnimal(@PathVariable("id") Long id) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
        Optional<Animal> existe = Optional.ofNullable(animalService.findById(id));
        if (existe.isPresent()) {
            animalService.delete(id);
            response.put("success", "Animal con ID " + id +" eliminado exitosamente");
            return ResponseEntity.ok(response);
        } else {
            errorResponse.put("error", "El animal con el ID" + id + "No Existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizaranimal(@PathVariable("id") Long id, @RequestBody Animal animal) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
        Optional<Animal> existeAnimal = Optional.ofNullable(animalService.findById(id));
        if (existeAnimal.isEmpty()) {
            errorResponse.put("error", "El animal con ID" + id + "no existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Optional<Animal> existeAnimalNombre = animalService.findByNombreAndEspecie(animal.getNombre(), animal.getEspecie());
        if (existeAnimalNombre.isPresent()) {
            errorResponse.put("error", "El nombre del animal ya es existente en esta especie");
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            Animal animalAux = existeAnimal.get();
            if (animal.getNombre() != null) {
                animalAux.setNombre(animal.getNombre());
            }
            if (animal.getEspecie() != null) {
                animalAux.setEspecie(animal.getEspecie());
            }
            animalService.update(animalAux, id);
            response.put("success", "El animal con ID " + id +" Modificado exitosamente");
            return ResponseEntity.ok(response);
        }

    }

}
