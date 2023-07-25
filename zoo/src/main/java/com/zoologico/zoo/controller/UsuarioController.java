package com.zoologico.zoo.controller;


import com.zoologico.zoo.entity.Usuario;
import com.zoologico.zoo.security.UserDetailServiceImpl;
import com.zoologico.zoo.service.PersonaService;
import com.zoologico.zoo.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PersonaService personaService;
    @Autowired
    private UserDetailServiceImpl userDetailServiceImpl;


    @PostMapping("/crearUsuario")
    public ResponseEntity<String> creaUsuario(@RequestBody Usuario usuario) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        //Crear nuevo
        Optional<Usuario> existe = usuarioService.findByEmail(usuario.getEmail());
        if (existe.isPresent()) {
            return ResponseEntity.badRequest().body("Usuario existente");
        }
        String password = usuario.getPassword();
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));

        if (usuario.getRol().getId() == 2 && "ADMIN".equals(rolUserLoad)) {
            usuarioService.save(usuario);
            return ResponseEntity.ok("Usuario Empleado creado exitosamente");
        } else if (usuario.getRol().getId() == 1 && "ADMIN".equals(rolUserLoad)) {
            usuarioService.save(usuario);
            return ResponseEntity.ok("Usuario ADMIN creado exitosamente");
        } else if (usuario.getRol().getId() == 2 || usuario.getRol().getId() > 2) {
            return ResponseEntity.badRequest().body("Rol no permitido");
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");

    }

    @GetMapping("/listar")
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        List<Usuario> usuarios = usuarioService.findAll();

        return ResponseEntity.ok(usuarios);
    }

    @DeleteMapping("/eliminar")
    public ResponseEntity<String> eliminarUsuario(@RequestParam("id") Long id) {
        String rolUserLoad = userDetailServiceImpl.getNombreRol();
        if (!"ADMIN".equals(rolUserLoad)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("NO autorizado");
        }
        usuarioService.delete(id);
        return ResponseEntity.ok("Usuario empleado eliminado exitosamente");
    }

}
