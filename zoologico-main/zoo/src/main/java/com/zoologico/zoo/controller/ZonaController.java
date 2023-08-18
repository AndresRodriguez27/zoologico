package com.zoologico.zoo.controller;

import com.zoologico.zoo.entity.Usuario;
import com.zoologico.zoo.entity.Zona;
import com.zoologico.zoo.security.UserDetailServiceImpl;
import com.zoologico.zoo.service.ZonaSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/zonas")
public class ZonaController {

    @Autowired
    private ZonaSerivice zonaSerivice;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @PostMapping("")
    public ResponseEntity<Map<String, Object>> crearZona(@RequestBody Zona zona) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Optional<Zona> existeZona = zonaSerivice.findByNombre(zona.getNombre());
        if (existeZona.isPresent()) {
            errorResponse.put("error", "El nombre de la zona ya es existente");
            return ResponseEntity.badRequest().body(errorResponse);
            //return ResponseEntity.badRequest().body("La zona ya existe");
        }
        zonaSerivice.save(zona);
        response.put("success", "Zona creada exitosamente");
        return ResponseEntity.ok(response);
        //return ResponseEntity.ok("Zona creada exitosamente");
    }

    @GetMapping("")
    public ResponseEntity<List<Zona>> listarZonas() {
        List<Zona> zonas = zonaSerivice.findAll();
        return ResponseEntity.ok(zonas);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminarZona(@PathVariable("id") Long id) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
            //return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Optional<Zona> existe = zonaSerivice.findById(id);
        if (existe.isPresent()) {
            zonaSerivice.delete(id);
            response.put("success", "La zona con ID " + id +" eliminada exitosamente");
            return ResponseEntity.ok(response);
        } else {
            errorResponse.put("error", "La Zona No Existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Map<String, Object>> actualizarZona(@PathVariable("id") Long id, @RequestBody Zona zona) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        String rolUserLoad = userDetailServiceImpl.getNombreRol();

        if (!"ADMIN".equals(rolUserLoad)) {
            errorResponse.put("error", "NO autorizado");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
        }
        Optional<Zona> existeZona = zonaSerivice.findById(id);
        if (existeZona.isEmpty()) {
            errorResponse.put("error", "La Zona No Existe");
            return ResponseEntity.badRequest().body(errorResponse);
        }
        Optional<Zona> existeZonaNombre = zonaSerivice.findByNombre(zona.getNombre());
        if (existeZonaNombre.isPresent()) {
            errorResponse.put("error", "El nombre de la zona ya es existente");
            return ResponseEntity.badRequest().body(errorResponse);
        } else {
        Zona zonaAux = existeZona.get();
        zonaAux.setNombre(zona.getNombre());
        zonaSerivice.update(zonaAux, id);
        response.put("success", "La zona con ID " + id +" Modificada exitosamente");
        return ResponseEntity.ok(response);
        }
    }

}
