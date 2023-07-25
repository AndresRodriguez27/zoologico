package com.zoologico.zoo.controller;

import com.zoologico.zoo.entity.Zona;
import com.zoologico.zoo.security.UserDetailServiceImpl;
import com.zoologico.zoo.service.ZonaSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/zonas")
public class ZonaController {

    @Autowired
    private ZonaSerivice zonaSerivice;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @PostMapping("/crear")
    public ResponseEntity<String> crearZona(@RequestBody Zona zona) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Optional<Zona> existeZona = zonaSerivice.findByNombre(zona.getNombre());
        if (existeZona.isPresent()) {
            return ResponseEntity.badRequest().body("La zona ya existe");
        }

        zonaSerivice.save(zona);
        return ResponseEntity.ok("Zona creada exitosamente");

    }

    @GetMapping("/listar")
    public ResponseEntity<List<Zona>> listarZonas() {

        List<Zona> zonas = zonaSerivice.findAll();

        return ResponseEntity.ok(zonas);
    }

    //@DeleteMapping("/eliminar")
    //public ResponseEntity<String> eliminarZona(@RequestParam("id") Long id) {
    //    zonaSerivice.delete(id);
    //    return ResponseEntity.ok("Zona eliminada exitosamente");
    //}

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarZona(@RequestParam("id") Long id) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        zonaSerivice.delete(id);
        return ResponseEntity.ok("Zona eliminada exitosamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarZona(@RequestBody Zona zona) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Zona existeZona = zonaSerivice.findById(zona.getId());
        if (existeZona != null) {
            zonaSerivice.update(zona);
            return ResponseEntity.ok("Zona actualizada exitosamente");
        } else {
            return ResponseEntity.badRequest().body("La Zona NO existe");
        }

    }

}
