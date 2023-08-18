package com.zoologico.zoo.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.zoologico.zoo.entity.Usuario;
import com.zoologico.zoo.security.UserDetailServiceImpl;
import com.zoologico.zoo.service.PersonaService;
import com.zoologico.zoo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;

    private final Logger logger = LoggerFactory.getLogger(UsuarioController.class);



    @PostMapping("")
    public ResponseEntity<Map<String, Object>> creaUsuario(@RequestBody Usuario usuario) {
        Map<String, Object> errorResponse = new HashMap<>();
        Map<String, Object> response = new HashMap<>();
        // verificar si algún campo necesario está vacío
        if (usuario.getEmail() == null || usuario.getEmail().isEmpty()
        || usuario.getPassword() == null || usuario.getPassword().isEmpty() || usuario.getRol() == null) {
            errorResponse.put("error", "Campos obligatorios faltantes");
            return ResponseEntity.badRequest().body(errorResponse);
//            return ResponseEntity.badRequest().body("Campos obligatorios faltantes");
        }

        Optional<Usuario> existe = usuarioService.findByEmail(usuario.getEmail());
        if (existe.isPresent()) {
            errorResponse.put("error", "Usuario existente");
            return ResponseEntity.badRequest().body(errorResponse);
//            return ResponseEntity.badRequest().body("Usuario existente");
        }

        //---Crear nuevo----
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        String password = usuario.getPassword();
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        if (usuario.getRol().getId() == 2 && "ADMIN".equals(rolUserLoad)) {
            usuarioService.save(usuario);
            response.put("success", "Usuario Empleado creado exitosamente");
            return ResponseEntity.ok(response);
//            return ResponseEntity.ok("Usuario Empleado creado exitosamente");
        }
//        else if (usuario.getRol().getId() == 1 && "ADMIN".equals(rolUserLoad)) {
//            usuarioService.save(usuario);
//            return ResponseEntity.ok("Usuario ADMIN creado exitosamente");
//        }
        else if (usuario.getRol().getId() == 2 || usuario.getRol().getId() > 2) {
            errorResponse.put("error", "Rol no permitido");
            return ResponseEntity.badRequest().body(errorResponse);
//            return ResponseEntity.badRequest().body("Rol no permitido");
        }
        errorResponse.put("error", "NO autorizado");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
//        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");

    }

    @GetMapping("")
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.findAll();
        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<Map<String, Object>> eliminarUsuario(@PathVariable("id") Long id) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
//        logger.debug("Valor de rolUserLoad: {}", rolUserLoad);
        Map<String, Object> response = new HashMap<>();
        Map<String, Object> errorResponse= new HashMap<>();
        if ("ADMIN".equals(rolUserLoad)) {
            Optional<Usuario> existe = usuarioService.findById(id);
            if (existe.isPresent()) {
                usuarioService.delete(id);
                response.put("success", "Usuario empleado con ID " + id +" eliminado exitosamente");
                return ResponseEntity.ok(response);
            } else {
                errorResponse.put("error", "Usuario No Existe");
                return ResponseEntity.badRequest().body(errorResponse);
            }
        }
        errorResponse.put("error", "NO autorizado");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
    }

}
