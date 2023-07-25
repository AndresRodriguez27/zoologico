package com.zoologico.zoo.controller;

import com.zoologico.zoo.entity.Especie;
import com.zoologico.zoo.entity.Zona;
import com.zoologico.zoo.security.UserDetailServiceImpl;
import com.zoologico.zoo.service.EspecieService;
import com.zoologico.zoo.service.ZonaSerivice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/especies")
public class EspecieController {
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    @Autowired
    private EspecieService especieService;

    @Autowired
    private ZonaSerivice zonaSerivice;

    @PostMapping("/crear")
    public ResponseEntity<String> crearZona(@RequestBody Especie especie) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Optional<Especie> existeEspecie = especieService.findByNombre(especie.getNombre());
        if (existeEspecie.isPresent()) {
            return ResponseEntity.badRequest().body("La especie ya existe");
        }

        especieService.save(especie);
        return ResponseEntity.ok("Zona creada exitosamente");

    }

    @GetMapping("/listar")
    public ResponseEntity<List<Especie>> listarZonas() {

        List<Especie> especies = especieService.findAll();

        return ResponseEntity.ok(especies);
    }


    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarZona(@RequestParam("id") Long id) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        especieService.delete(id);
        return ResponseEntity.ok("Zona eliminada exitosamente");
    }

    @PutMapping("/actualizar")
    public ResponseEntity<String> actualizarZona(@RequestBody Zona zona) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Especie existeEspecie = especieService.findById(zona.getId());
        if (existeEspecie != null) {
            especieService.update(zona);
            return ResponseEntity.ok("Zona actualizada exitosamente");
        } else {
            return ResponseEntity.badRequest().body("La Zona NO existe");
        }

    }

    @PutMapping("/asociarEspecie")
    public ResponseEntity<String> asociarEspecie(@RequestParam("idZona") Long idZona, @RequestParam("idEspecie") Long idEspecie) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        Zona zona = zonaSerivice.findById(idZona);
        if (zona == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("La zona NO existe");
        }
        Especie especie = especieService.findById(idEspecie);
        especie.setZona(zona);
        especieService.update(zona);

        return ResponseEntity.ok("Asociado exitosamente");

    }
}
