package com.zoologico.zoo.controller;

import com.zoologico.zoo.entity.Especie;
import com.zoologico.zoo.entity.Usuario;
import com.zoologico.zoo.entity.Zona;
import com.zoologico.zoo.security.UserDetailServiceImpl;
import com.zoologico.zoo.service.EspecieService;
import com.zoologico.zoo.service.ZonaSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/especies")
public class EspecieController {
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private EspecieService especieService;

    @Autowired
    private ZonaSerivice zonaSerivice;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> crearZona(@RequestBody Especie especie) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
        Optional<Especie> existeEspecie = especieService.findByNombreAndZona(especie.getNombre(), especie.getZona());

        if (existeEspecie.isPresent()) {
            errorResponse.put("error", "El nombre de la especie ya es existente en la misma zona");
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
        especieService.save(especie);
        response.put("success", "Especie creada exitosamente");
        return ResponseEntity.ok(response);
        }
    }

    @GetMapping("")
    public ResponseEntity<List<Especie>> listarZonas() {
        List<Especie> especies = especieService.findAll();
        return ResponseEntity.ok(especies);
    }


    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminarZona(@PathVariable("id") Long id) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
        Optional<Especie> existe = especieService.findById(id);
        if (existe.isPresent()) {
            especieService.delete(id);
            response.put("success", "Especie con ID " + id +" eliminada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            errorResponse.put("error", "La especie No Existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizarZona(@PathVariable("id") Long id, @RequestBody Especie especie) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
        Optional<Especie> existeEspecie = especieService.findById(id);
        if (existeEspecie.isEmpty()) {
            errorResponse.put("error", "La especie con ID" + id + "no existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Optional<Especie> existeEspecieNombre = especieService.findByNombreAndZona(especie.getNombre(), especie.getZona());
        if (existeEspecieNombre.isPresent()) {
            errorResponse.put("error", "El nombre de la especie ya es existente en la misma zona");
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
            Especie especieAux = existeEspecie.get();
            if (especie.getNombre() != null) {
                especieAux.setNombre(especie.getNombre());
            }
            if (especie.getZona() != null) {
                especieAux.setZona(especie.getZona());
            }
            especieService.update(especieAux, id);
            response.put("success", "La especie con ID " + id +" Modificada exitosamente");
            return ResponseEntity.ok(response);
        }
    }
}
